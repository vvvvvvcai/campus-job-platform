package com.campus.recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import com.campus.recommend.dto.RecommendRefreshDTO;
import com.campus.recommend.entity.RecommendLog;
import com.campus.recommend.entity.UserProfile;
import com.campus.recommend.feign.JobFeignClient;
import com.campus.recommend.feign.UserFeignClient;
import com.campus.recommend.mapper.RecommendLogMapper;
import com.campus.recommend.mapper.UserProfileMapper;
import com.campus.recommend.service.RecommendService;
import com.campus.recommend.vo.RecommendJobVO;
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

/**
 * 推荐服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final UserProfileMapper userProfileMapper;
    private final RecommendLogMapper recommendLogMapper;
    private final JobFeignClient jobFeignClient;
    private final UserFeignClient userFeignClient;
    private final StringRedisTemplate redisTemplate;

    private static final int CITY_SCORE = 40;
    private static final int INDUSTRY_SCORE = 30;
    private static final int JOB_TYPE_SCORE = 20;
    private static final int POPULARITY_MAX_SCORE = 10;
    private static final int MAX_RECOMMEND_SIZE = 10;
    private static final String RECOMMEND_CACHE_KEY = "recommend:user:";

    @Override
    public Result<List<RecommendJobVO>> getRecommendJobs(Long userId) {
        // 先从缓存获取
        String cacheKey = RECOMMEND_CACHE_KEY + userId;
        List<RecommendJobVO> cachedJobs = getCachedRecommend(cacheKey);
        if (cachedJobs != null && !cachedJobs.isEmpty()) {
            return Result.success(cachedJobs);
        }

        // 获取用户画像
        UserProfile userProfile = getUserProfile(userId);

        // 获取所有在线职位
        List<Map<String, Object>> allJobs = getAllJobs();
        if (allJobs == null || allJobs.isEmpty()) {
            return Result.success(Collections.emptyList());
        }

        // 计算推荐分数
        List<RecommendJobVO> recommendJobs = calculateScores(userProfile, allJobs);

        // 排序并取Top10
        List<RecommendJobVO> topJobs = recommendJobs.stream()
                .sorted(Comparator.comparing(RecommendJobVO::getRecommendScore).reversed())
                .limit(MAX_RECOMMEND_SIZE)
                .collect(Collectors.toList());

        // 缓存结果
        cacheRecommend(cacheKey, topJobs);

        return Result.success(topJobs);
    }

    @Override
    public Result<List<RecommendJobVO>> refreshRecommend(RecommendRefreshDTO dto) {
        // 清除缓存
        String cacheKey = RECOMMEND_CACHE_KEY + dto.getUserId();
        redisTemplate.delete(cacheKey);

        // 获取用户画像
        UserProfile userProfile = getUserProfile(dto.getUserId());

        // 如果DTO中有筛选条件，覆盖用户画像
        if (dto.getCity() != null && !dto.getCity().isEmpty()) {
            userProfile.setIntentCity(dto.getCity());
        }
        if (dto.getIndustry() != null && !dto.getIndustry().isEmpty()) {
            userProfile.setIntentIndustry(dto.getIndustry());
        }
        if (dto.getJobType() != null && !dto.getJobType().isEmpty()) {
            userProfile.setIntentJobType(dto.getJobType());
        }

        // 获取所有在线职位
        List<Map<String, Object>> allJobs = getAllJobs();
        if (allJobs == null || allJobs.isEmpty()) {
            return Result.success(Collections.emptyList());
        }

        // 计算推荐分数
        List<RecommendJobVO> recommendJobs = calculateScores(userProfile, allJobs);

        // 排序并取Top10
        List<RecommendJobVO> topJobs = recommendJobs.stream()
                .sorted(Comparator.comparing(RecommendJobVO::getRecommendScore).reversed())
                .limit(MAX_RECOMMEND_SIZE)
                .collect(Collectors.toList());

        // 缓存结果
        cacheRecommend(cacheKey, topJobs);

        return Result.success(topJobs);
    }

    @Override
    public Result<Void> recordClick(Long userId, Long jobId) {
        // 查找推荐日志
        LambdaQueryWrapper<RecommendLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RecommendLog::getUserId, userId)
                .eq(RecommendLog::getJobId, jobId);
        RecommendLog log = recommendLogMapper.selectOne(wrapper);

        if (log != null) {
            log.setIsClicked(1);
            recommendLogMapper.updateById(log);
        } else {
            RecommendLog recommendLog = new RecommendLog();
            recommendLog.setUserId(userId);
            recommendLog.setJobId(jobId);
            recommendLog.setIsClicked(1);
            recommendLog.setIsApplied(0);
            recommendLogMapper.insert(recommendLog);
        }

        return Result.success();
    }

    /**
     * 获取用户画像
     */
    private UserProfile getUserProfile(Long userId) {
        LambdaQueryWrapper<UserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserProfile::getUserId, userId);
        UserProfile profile = userProfileMapper.selectOne(wrapper);

        if (profile == null) {
            // 创建默认用户画像
            profile = new UserProfile();
            profile.setUserId(userId);
            profile.setBrowseCount(0);
            profile.setApplyCount(0);
            profile.setFavoriteCount(0);
            userProfileMapper.insert(profile);
        }

        // 如果用户画像缺少意向信息，尝试从用户服务获取
        if (profile.getIntentCity() == null || profile.getIntentIndustry() == null) {
            try {
                Result<Map<String, Object>> userResult = userFeignClient.getUserInfo(userId);
                if (userResult != null && userResult.getData() != null) {
                    Map<String, Object> userData = userResult.getData();
                    // 从用户信息中提取意向信息（根据实际数据结构调整）
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

    /**
     * 获取所有在线职位
     */
    private List<Map<String, Object>> getAllJobs() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("status", 1);
            params.put("auditStatus", 1);
            Result<Map<String, Object>> result = jobFeignClient.getJobList(params);
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

    /**
     * 计算推荐分数
     */
    private List<RecommendJobVO> calculateScores(UserProfile userProfile, List<Map<String, Object>> jobs) {
        List<RecommendJobVO> recommendJobs = new ArrayList<>();

        for (Map<String, Object> job : jobs) {
            RecommendJobVO vo = new RecommendJobVO();
            vo.setJobId(toLong(job.get("id")));
            vo.setTitle((String) job.get("title"));
            vo.setCity((String) job.get("city"));
            vo.setSalaryMin(toBigDecimal(job.get("salaryMin")));
            vo.setSalaryMax(toBigDecimal(job.get("salaryMax")));

            // 获取企业名称
            Object companyName = job.get("companyName");
            if (companyName == null) {
                companyName = job.get("company_name");
            }
            vo.setCompanyName(companyName != null ? companyName.toString() : "");

            // 计算推荐分数
            BigDecimal score = BigDecimal.ZERO;
            StringBuilder reason = new StringBuilder();

            // 城市匹配 +40
            if (userProfile.getIntentCity() != null && !userProfile.getIntentCity().isEmpty()
                    && userProfile.getIntentCity().equals(vo.getCity())) {
                score = score.add(BigDecimal.valueOf(CITY_SCORE));
                reason.append("符合您的意向城市;");
            }

            // 行业匹配 +30
            String jobCategory = (String) job.get("category");
            if (userProfile.getIntentIndustry() != null && !userProfile.getIntentIndustry().isEmpty()
                    && jobCategory != null && jobCategory.contains(userProfile.getIntentIndustry())) {
                score = score.add(BigDecimal.valueOf(INDUSTRY_SCORE));
                reason.append("符合您的意向行业;");
            }

            // 职位类型匹配 +20
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

            // 热度分 +10（基于浏览次数和申请次数归一化）
            Integer viewCount = toInteger(job.get("viewCount"));
            Integer applyCount = toInteger(job.get("applyCount"));
            if (viewCount != null && applyCount != null) {
                double popularity = Math.min((viewCount + applyCount) / 100.0, 1.0) * POPULARITY_MAX_SCORE;
                score = score.add(BigDecimal.valueOf(popularity).setScale(2, RoundingMode.HALF_UP));
                reason.append("热门职位;");
            }

            vo.setRecommendScore(score.setScale(2, RoundingMode.HALF_UP));
            vo.setRecommendReason(reason.length() > 0 ? reason.substring(0, reason.length() - 1) : "系统推荐");

            recommendJobs.add(vo);
        }

        return recommendJobs;
    }

    /**
     * 缓存推荐结果
     */
    private void cacheRecommend(String cacheKey, List<RecommendJobVO> jobs) {
        try {
            // 简单序列化存储（实际项目建议使用JSON序列化）
            StringBuilder sb = new StringBuilder();
            for (RecommendJobVO job : jobs) {
                sb.append(job.getJobId()).append(",")
                        .append(job.getTitle()).append(",")
                        .append(job.getCompanyName()).append(",")
                        .append(job.getCity()).append(",")
                        .append(job.getSalaryMin()).append(",")
                        .append(job.getSalaryMax()).append(",")
                        .append(job.getRecommendScore()).append(",")
                        .append(job.getRecommendReason()).append("|");
            }
            redisTemplate.opsForValue().set(cacheKey, sb.toString(), 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.warn("缓存推荐结果失败: {}", e.getMessage());
        }
    }

    /**
     * 获取缓存的推荐结果
     */
    private List<RecommendJobVO> getCachedRecommend(String cacheKey) {
        try {
            String cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached == null || cached.isEmpty()) {
                return null;
            }
            List<RecommendJobVO> jobs = new ArrayList<>();
            String[] items = cached.split("\\|");
            for (String item : items) {
                if (item.isEmpty()) continue;
                String[] parts = item.split(",", -1);
                if (parts.length >= 8) {
                    RecommendJobVO vo = new RecommendJobVO();
                    vo.setJobId(Long.parseLong(parts[0]));
                    vo.setTitle(parts[1]);
                    vo.setCompanyName(parts[2]);
                    vo.setCity(parts[3]);
                    vo.setSalaryMin(new BigDecimal(parts[4]));
                    vo.setSalaryMax(new BigDecimal(parts[5]));
                    vo.setRecommendScore(new BigDecimal(parts[6]));
                    vo.setRecommendReason(parts[7]);
                    jobs.add(vo);
                }
            }
            return jobs;
        } catch (Exception e) {
            log.warn("获取缓存推荐结果失败: {}", e.getMessage());
            return null;
        }
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
