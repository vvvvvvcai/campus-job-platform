package com.campus.recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.common.result.Result;
import com.campus.recommend.client.ZhipuAiClient;
import com.campus.recommend.config.ZhipuAiProperties;
import com.campus.recommend.dto.RecommendRefreshDTO;
import com.campus.recommend.entity.RecommendLog;
import com.campus.recommend.entity.UserProfile;
import com.campus.recommend.feign.ApplicationFeignClient;
import com.campus.recommend.feign.JobFeignClient;
import com.campus.recommend.feign.UserFeignClient;
import com.campus.recommend.mapper.RecommendLogMapper;
import com.campus.recommend.mapper.UserProfileMapper;
import com.campus.recommend.service.RecommendService;
import com.campus.recommend.vo.RecommendJobVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final UserProfileMapper userProfileMapper;
    private final RecommendLogMapper recommendLogMapper;
    private final JobFeignClient jobFeignClient;
    private final UserFeignClient userFeignClient;
    private final ApplicationFeignClient applicationFeignClient;
    private final StringRedisTemplate redisTemplate;
    private final ZhipuAiClient zhipuAiClient;
    private final ZhipuAiProperties aiProperties;
    private final ObjectMapper objectMapper;

    private static final int CITY_SCORE = 40;
    private static final int INDUSTRY_SCORE = 30;
    private static final int JOB_TYPE_SCORE = 20;
    private static final int POPULARITY_MAX_SCORE = 10;
    private static final int MAX_RECOMMEND_SIZE = 10;
    private static final String RECOMMEND_CACHE_KEY = "recommend:user:";

    private static final String AI_SYSTEM_PROMPT =
            "你是一个专业的校园招聘职位推荐引擎。你的任务是根据候选人的简历信息和候选职位列表，" +
            "深度分析简历中的技能、专业、学历、项目经历与职位的匹配程度，推荐最合适的职位。\n" +
            "你必须严格按照要求返回JSON格式，不要输出任何其他内容。\n" +
            "评分维度及权重：技能匹配(35%) > 专业匹配(20%) > 学历匹配(15%) > 经验匹配(15%) > 意向匹配(15%)。";

    @Override
    public Result<List<RecommendJobVO>> getRecommendJobs(Long userId) {
        String cacheKey = RECOMMEND_CACHE_KEY + userId;
        List<RecommendJobVO> cachedJobs = getCachedRecommend(cacheKey);
        if (cachedJobs != null && !cachedJobs.isEmpty()) {
            return Result.success(cachedJobs);
        }

        UserProfile userProfile = getUserProfile(userId);
        Map<String, Object> resume = getDefaultResume(userId);
        List<Map<String, Object>> allJobs = getAllJobs();
        if (allJobs == null || allJobs.isEmpty()) {
            return Result.success(Collections.emptyList());
        }

        List<RecommendJobVO> topJobs;

        if (aiProperties.isEnabled()) {
            topJobs = getAiRecommendations(userProfile, resume, allJobs);
            if (topJobs == null || topJobs.isEmpty()) {
                log.info("AI推荐失败，降级到本地规则评分");
                topJobs = getLocalRecommendations(userProfile, allJobs);
            }
        } else {
            topJobs = getLocalRecommendations(userProfile, allJobs);
        }

        cacheRecommend(cacheKey, topJobs);
        return Result.success(topJobs);
    }

    @Override
    public Result<List<RecommendJobVO>> refreshRecommend(RecommendRefreshDTO dto) {
        String cacheKey = RECOMMEND_CACHE_KEY + dto.getUserId();
        redisTemplate.delete(cacheKey);

        UserProfile userProfile = getUserProfile(dto.getUserId());

        if (dto.getCity() != null && !dto.getCity().isEmpty()) {
            userProfile.setIntentCity(dto.getCity());
        }
        if (dto.getIndustry() != null && !dto.getIndustry().isEmpty()) {
            userProfile.setIntentIndustry(dto.getIndustry());
        }
        if (dto.getJobType() != null && !dto.getJobType().isEmpty()) {
            userProfile.setIntentJobType(dto.getJobType());
        }

        Map<String, Object> resume = getDefaultResume(dto.getUserId());
        List<Map<String, Object>> allJobs = getAllJobs();
        if (allJobs == null || allJobs.isEmpty()) {
            return Result.success(Collections.emptyList());
        }

        List<RecommendJobVO> topJobs;

        if (aiProperties.isEnabled()) {
            topJobs = getAiRecommendations(userProfile, resume, allJobs);
            if (topJobs == null || topJobs.isEmpty()) {
                log.info("AI刷新推荐失败，降级到本地规则评分");
                topJobs = getLocalRecommendations(userProfile, allJobs);
            }
        } else {
            topJobs = getLocalRecommendations(userProfile, allJobs);
        }

        cacheRecommend(cacheKey, topJobs);
        return Result.success(topJobs);
    }

    @Override
    public Result<Void> recordClick(Long userId, Long jobId) {
        LambdaQueryWrapper<RecommendLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RecommendLog::getUserId, userId)
                .eq(RecommendLog::getJobId, jobId);
        RecommendLog logEntity = recommendLogMapper.selectOne(wrapper);

        if (logEntity != null) {
            logEntity.setIsClicked(1);
            recommendLogMapper.updateById(logEntity);
        } else {
            RecommendLog recommendLog = new RecommendLog();
            recommendLog.setUserId(userId);
            recommendLog.setJobId(jobId);
            recommendLog.setIsClicked(1);
            recommendLog.setIsApplied(0);
            recommendLogMapper.insert(recommendLog);
        }

        try {
            LambdaQueryWrapper<UserProfile> profileWrapper = new LambdaQueryWrapper<>();
            profileWrapper.eq(UserProfile::getUserId, userId);
            UserProfile profile = userProfileMapper.selectOne(profileWrapper);
            if (profile != null) {
                profile.setBrowseCount(profile.getBrowseCount() + 1);
                profile.setLastBrowseTime(LocalDateTime.now());
                userProfileMapper.updateById(profile);
            }
        } catch (Exception e) {
            log.warn("更新用户浏览次数失败: {}", e.getMessage());
        }

        return Result.success();
    }

    /**
     * 获取用户默认简历
     */
    private Map<String, Object> getDefaultResume(Long userId) {
        try {
            Result<List<Map<String, Object>>> result = applicationFeignClient.getResumeList(userId);
            if (result != null && result.getData() != null) {
                List<Map<String, Object>> resumes = result.getData();
                for (Map<String, Object> resume : resumes) {
                    Object isDefault = resume.get("isDefault");
                    if (isDefault != null && (isDefault instanceof Integer ? (Integer) isDefault == 1 : Integer.parseInt(isDefault.toString()) == 1)) {
                        return resume;
                    }
                }
                if (!resumes.isEmpty()) {
                    return resumes.get(0);
                }
            }
        } catch (Exception e) {
            log.warn("获取用户简历失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 调用智谱AI进行智能推荐（基于简历）
     */
    private List<RecommendJobVO> getAiRecommendations(UserProfile userProfile, Map<String, Object> resume, List<Map<String, Object>> allJobs) {
        try {
            List<Map<String, Object>> jobsToSend = allJobs.stream()
                    .limit(aiProperties.getMaxJobs())
                    .collect(Collectors.toList());

            String userPrompt = buildAiPrompt(userProfile, resume, jobsToSend);
            String aiResponse = zhipuAiClient.chatCompletion(AI_SYSTEM_PROMPT, userPrompt);

            if (aiResponse == null) {
                return null;
            }

            List<Map<String, Object>> aiResults = zhipuAiClient.parseRecommendResults(aiResponse);
            if (aiResults.isEmpty()) {
                return null;
            }

            Map<Long, Map<String, Object>> jobMap = new HashMap<>();
            for (Map<String, Object> job : jobsToSend) {
                Object id = job.get("id");
                if (id != null) {
                    jobMap.put(toLong(id), job);
                }
            }

            List<RecommendJobVO> result = new ArrayList<>();
            for (Map<String, Object> aiItem : aiResults) {
                Long jobId = toLong(aiItem.get("jobId"));
                Map<String, Object> jobData = jobMap.get(jobId);
                if (jobData == null) continue;

                RecommendJobVO vo = new RecommendJobVO();
                vo.setJobId(jobId);
                vo.setTitle((String) jobData.get("title"));
                vo.setCity((String) jobData.get("city"));
                vo.setSalaryMin(toBigDecimal(jobData.get("salaryMin")));
                vo.setSalaryMax(toBigDecimal(jobData.get("salaryMax")));

                Object companyName = jobData.get("companyName");
                if (companyName == null) {
                    companyName = jobData.get("company_name");
                }
                vo.setCompanyName(companyName != null ? companyName.toString() : "");

                Object scoreObj = aiItem.get("score");
                BigDecimal score = scoreObj != null
                        ? new BigDecimal(scoreObj.toString()).setScale(2, RoundingMode.HALF_UP)
                        : BigDecimal.ZERO;
                vo.setRecommendScore(score);

                String reason = aiItem.get("reason") != null
                        ? aiItem.get("reason").toString()
                        : "AI智能推荐";
                vo.setRecommendReason(reason);

                vo.setCategory((String) jobData.get("category"));
                vo.setIndustry((String) jobData.get("industry"));
                vo.setJobType(toInteger(jobData.get("jobType")));

                result.add(vo);
            }

            result.sort(Comparator.comparing(RecommendJobVO::getRecommendScore).reversed());
            log.info("AI推荐成功，返回{}个职位（基于简历匹配）", result.size());
            return result;

        } catch (Exception e) {
            log.error("AI推荐异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 构建AI推荐的Prompt（含简历信息）
     */
    private String buildAiPrompt(UserProfile userProfile, Map<String, Object> resume, List<Map<String, Object>> jobs) {
        StringBuilder sb = new StringBuilder();

        sb.append("## 用户画像\n");
        sb.append("- 意向城市：").append(userProfile.getIntentCity() != null ? userProfile.getIntentCity() : "未设置").append("\n");
        sb.append("- 意向行业：").append(userProfile.getIntentIndustry() != null ? userProfile.getIntentIndustry() : "未设置").append("\n");
        sb.append("- 意向职位类型：").append(userProfile.getIntentJobType() != null ? userProfile.getIntentJobType() : "未设置").append("\n");
        sb.append("- 历史行为：浏览").append(userProfile.getBrowseCount()).append("次，")
                .append("投递").append(userProfile.getApplyCount()).append("次，")
                .append("收藏").append(userProfile.getFavoriteCount()).append("次\n\n");

        if (resume != null) {
            sb.append("## 简历详情\n");
            appendResumeField(sb, "姓名", resume.get("name"));
            appendResumeField(sb, "学历", resume.get("education"));
            appendResumeField(sb, "院校", resume.get("school"));
            appendResumeField(sb, "专业", resume.get("major"));
            appendResumeField(sb, "毕业年份", resume.get("graduationYear"));
            appendResumeField(sb, "工作状态", formatWorkStatus(resume.get("workStatus")));
            appendResumeField(sb, "专业技能", resume.get("skills"));
            appendResumeField(sb, "自我介绍", resume.get("selfIntroduction"));
            appendResumeField(sb, "工作/实习经历", resume.get("experiences"));
            appendResumeField(sb, "项目经历", resume.get("projects"));
            appendResumeField(sb, "获奖情况", resume.get("awards"));
            appendResumeField(sb, "期望城市", resume.get("expectCity"));
            appendResumeField(sb, "期望行业", resume.get("expectIndustry"));
            appendResumeField(sb, "期望岗位", resume.get("expectJobType"));
            appendResumeField(sb, "期望薪资", resume.get("expectSalary"));
            sb.append("\n");
        }

        sb.append("## 候选职位（共").append(jobs.size()).append("个）\n");
        sb.append("[\n");
        for (int i = 0; i < jobs.size(); i++) {
            Map<String, Object> job = jobs.get(i);
            sb.append("  {\"id\":").append(toLong(job.get("id")))
                    .append(",\"title\":\"").append(escapeJson((String) job.get("title"))).append("\"")
                    .append(",\"city\":\"").append(escapeJson((String) job.get("city"))).append("\"")
                    .append(",\"salaryMin\":").append(toBigDecimal(job.get("salaryMin")))
                    .append(",\"salaryMax\":").append(toBigDecimal(job.get("salaryMax")))
                    .append(",\"category\":\"").append(escapeJson((String) job.get("category"))).append("\"")
                    .append(",\"education\":\"").append(escapeJson((String) job.get("education"))).append("\"")
                    .append(",\"experience\":\"").append(escapeJson((String) job.get("experience"))).append("\"")
                    .append(",\"description\":\"").append(escapeJson(truncate((String) job.get("description"), 200))).append("\"")
                    .append(",\"jobType\":").append(toInteger(job.get("jobType")))
                    .append(",\"viewCount\":").append(toInteger(job.get("viewCount")))
                    .append(",\"applyCount\":").append(toInteger(job.get("applyCount")))
                    .append("}");
            if (i < jobs.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]\n\n");

        sb.append("## 匹配要求\n");
        sb.append("请根据以下维度评估每个职位与候选人的匹配度（score 0-100）：\n");
        sb.append("1. 技能匹配（权重35%）：候选人的skills与职位所需技术栈/技能的重合度\n");
        sb.append("2. 专业匹配（权重20%）：所学专业major与职位行业category的相关性\n");
        sb.append("3. 学历匹配（权重15%）：学历education是否满足职位要求\n");
        sb.append("4. 经验匹配（权重15%）：项目经历projects、实习经历experiences与岗位的相关度\n");
        sb.append("5. 意向匹配（权重15%）：期望城市expectCity、期望行业expectIndustry、期望岗位expectJobType与职位的吻合度\n\n");
        sb.append("## 输出要求\n");
        sb.append("1. 从候选职位中选出匹配度最高的Top10职位\n");
        sb.append("2. 按score从高到低排序\n");
        sb.append("3. 返回严格的JSON数组格式，每个元素包含：jobId(Long), score(Double, 0-100), reason(String, 20字以内的推荐理由，说明匹配点）\n\n");
        sb.append("返回格式示例：\n");
        sb.append("[{\"jobId\":1,\"score\":95.5,\"reason\":\"技能高度匹配且专业对口\"},{\"jobId\":2,\"score\":88.0,\"reason\":\"项目经验丰富且意向城市一致\"}]");

        return sb.toString();
    }

    private void appendResumeField(StringBuilder sb, String label, Object value) {
        if (value != null && !value.toString().isEmpty()) {
            String str = value.toString();
            if (str.length() > 500) {
                str = str.substring(0, 500) + "...";
            }
            sb.append("- ").append(label).append("：").append(escapeJson(str)).append("\n");
        }
    }

    private String formatWorkStatus(Object status) {
        if (status == null) return null;
        int code = status instanceof Integer ? (Integer) status : Integer.parseInt(status.toString());
        switch (code) {
            case 0: return "在校生";
            case 1: return "应届毕业生";
            case 2: return "有工作经验";
            default: return null;
        }
    }

    private String truncate(String text, int maxLen) {
        if (text == null) return null;
        return text.length() > maxLen ? text.substring(0, maxLen) + "..." : text;
    }

    /**
     * 本地规则评分（降级方案）
     */
    private List<RecommendJobVO> getLocalRecommendations(UserProfile userProfile, List<Map<String, Object>> allJobs) {
        List<RecommendJobVO> recommendJobs = calculateScores(userProfile, allJobs);
        return recommendJobs.stream()
                .sorted(Comparator.comparing(RecommendJobVO::getRecommendScore).reversed())
                .limit(MAX_RECOMMEND_SIZE)
                .collect(Collectors.toList());
    }

    private UserProfile getUserProfile(Long userId) {
        LambdaQueryWrapper<UserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserProfile::getUserId, userId);
        UserProfile profile = userProfileMapper.selectOne(wrapper);

        if (profile == null) {
            profile = new UserProfile();
            profile.setUserId(userId);
            profile.setBrowseCount(0);
            profile.setApplyCount(0);
            profile.setFavoriteCount(0);
            userProfileMapper.insert(profile);
        }

        if (profile.getIntentCity() == null || profile.getIntentIndustry() == null) {
            try {
                Result<Map<String, Object>> userResult = userFeignClient.getUserInfo(userId);
                if (userResult != null && userResult.getData() != null) {
                    Map<String, Object> userData = userResult.getData();
                    if (profile.getIntentCity() == null && userData.containsKey("intentCity")) {
                        profile.setIntentCity((String) userData.get("intentCity"));
                    }
                    if (profile.getIntentIndustry() == null && userData.containsKey("intentIndustry")) {
                        profile.setIntentIndustry((String) userData.get("intentIndustry"));
                    }
                    userProfileMapper.updateById(profile);
                }
            } catch (Exception e) {
                log.warn("获取用户信息失败: {}", e.getMessage());
            }
        }

        return profile;
    }

    private List<Map<String, Object>> getAllJobs() {
        try {
            Result<Map<String, Object>> result = jobFeignClient.searchJobs(1, 50);
            if (result != null && result.getData() != null) {
                Object records = result.getData().get("records");
                if (records instanceof List) {
                    return (List<Map<String, Object>>) records;
                }
            }
        } catch (Exception e) {
            log.error("获取职位列表失败: {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    private List<RecommendJobVO> calculateScores(UserProfile userProfile, List<Map<String, Object>> jobs) {
        List<RecommendJobVO> recommendJobs = new ArrayList<>();

        for (Map<String, Object> job : jobs) {
            RecommendJobVO vo = new RecommendJobVO();
            vo.setJobId(toLong(job.get("id")));
            vo.setTitle((String) job.get("title"));
            vo.setCity((String) job.get("city"));
            vo.setSalaryMin(toBigDecimal(job.get("salaryMin")));
            vo.setSalaryMax(toBigDecimal(job.get("salaryMax")));

            Object companyName = job.get("companyName");
            if (companyName == null) {
                companyName = job.get("company_name");
            }
            vo.setCompanyName(companyName != null ? companyName.toString() : "");

            BigDecimal score = BigDecimal.ZERO;
            StringBuilder reason = new StringBuilder();

            if (userProfile.getIntentCity() != null && !userProfile.getIntentCity().isEmpty()
                    && userProfile.getIntentCity().equals(vo.getCity())) {
                score = score.add(BigDecimal.valueOf(CITY_SCORE));
                reason.append("符合您的意向城市;");
            }

            String jobCategory = (String) job.get("category");
            if (userProfile.getIntentIndustry() != null && !userProfile.getIntentIndustry().isEmpty()
                    && jobCategory != null && jobCategory.contains(userProfile.getIntentIndustry())) {
                score = score.add(BigDecimal.valueOf(INDUSTRY_SCORE));
                reason.append("符合您的意向行业;");
            }

            Integer jobType = toInteger(job.get("jobType"));
            if (userProfile.getIntentJobType() != null && !userProfile.getIntentJobType().isEmpty()
                    && jobType != null) {
                String intentType = userProfile.getIntentJobType();
                if (("全职".equals(intentType) && jobType == 1)
                        || ("实习".equals(intentType) && jobType == 2)
                        || ("兼职".equals(intentType) && jobType == 3)) {
                    score = score.add(BigDecimal.valueOf(JOB_TYPE_SCORE));
                    reason.append("符合您的意向职位类型;");
                }
            }

            Integer viewCount = toInteger(job.get("viewCount"));
            Integer applyCount = toInteger(job.get("applyCount"));
            if (viewCount != null && applyCount != null) {
                double popularity = Math.min((viewCount + applyCount) / 100.0, 1.0) * POPULARITY_MAX_SCORE;
                score = score.add(BigDecimal.valueOf(popularity).setScale(2, RoundingMode.HALF_UP));
                reason.append("热门职位;");
            }

            vo.setRecommendScore(score.setScale(2, RoundingMode.HALF_UP));
            vo.setRecommendReason(reason.length() > 0 ? reason.substring(0, reason.length() - 1) : "系统推荐");
            vo.setCategory(jobCategory);
            vo.setIndustry((String) job.get("industry"));
            vo.setJobType(jobType);

            recommendJobs.add(vo);
        }

        return recommendJobs;
    }

    private void cacheRecommend(String cacheKey, List<RecommendJobVO> jobs) {
        try {
            String json = objectMapper.writeValueAsString(jobs);
            redisTemplate.opsForValue().set(cacheKey, json, aiProperties.getCacheMinutes(), TimeUnit.MINUTES);
        } catch (Exception e) {
            log.warn("缓存推荐结果失败: {}", e.getMessage());
        }
    }

    private List<RecommendJobVO> getCachedRecommend(String cacheKey) {
        try {
            String cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached == null || cached.isEmpty()) {
                return null;
            }
            return objectMapper.readValue(cached, new TypeReference<List<RecommendJobVO>>() {});
        } catch (Exception e) {
            log.warn("获取缓存推荐结果失败: {}", e.getMessage());
            return null;
        }
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    private Long toLong(Object value) {
        if (value == null) return 0L;
        if (value instanceof Long) return (Long) value;
        if (value instanceof Number) return ((Number) value).longValue();
        return Long.parseLong(value.toString());
    }

    private Integer toInteger(Object value) {
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Number) return ((Number) value).intValue();
        return Integer.parseInt(value.toString());
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) return BigDecimal.ZERO;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        if (value instanceof Number) return BigDecimal.valueOf(((Number) value).doubleValue());
        return new BigDecimal(value.toString());
    }
}
