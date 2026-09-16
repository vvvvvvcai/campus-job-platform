package com.campus.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.common.enums.JobEnum;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import com.campus.job.dto.JobAuditDTO;
import com.campus.job.dto.JobPublishDTO;
import com.campus.job.dto.JobSearchDTO;
import com.campus.job.entity.Company;
import com.campus.job.entity.Job;
import com.campus.job.mapper.CompanyMapper;
import com.campus.job.mapper.JobMapper;
import com.campus.job.service.JobService;
import com.campus.job.vo.JobAdminListVO;
import com.campus.job.vo.JobInfoVO;
import com.campus.job.vo.JobListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 职位服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;
    private final CompanyMapper companyMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public Result<Void> publishJob(JobPublishDTO dto) {
        Job job = new Job();
        BeanUtils.copyProperties(dto, job);
        job.setStatus(JobEnum.Status.PENDING.getCode());
        job.setAuditStatus(JobEnum.AuditStatus.PENDING.getCode());
        job.setViewCount(0);
        job.setApplyCount(0);

        jobMapper.insert(job);
        log.info("职位发布成功: {}", job.getId());

        return Result.success();
    }

    @Override
    public Result<Void> updateJob(Long id, JobPublishDTO dto) {
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }

        BeanUtils.copyProperties(dto, job);
        jobMapper.updateById(job);
        log.info("职位更新成功: {}", id);

        return Result.success();
    }

    @Override
    public Result<JobListVO> searchJobs(JobSearchDTO dto) {
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Job::getStatus, JobEnum.Status.RECRUITING.getCode());
        wrapper.eq(Job::getDeleted, 0);

        if (StringUtils.hasText(dto.getKeyword())) {
            wrapper.and(w -> w.like(Job::getTitle, dto.getKeyword())
                    .or().like(Job::getDescription, dto.getKeyword()));
        }
        if (StringUtils.hasText(dto.getCity())) {
            wrapper.eq(Job::getCity, dto.getCity());
        }
        if (StringUtils.hasText(dto.getIndustry())) {
            wrapper.eq(Job::getIndustry, dto.getIndustry());
        }
        if (StringUtils.hasText(dto.getCategory())) {
            wrapper.eq(Job::getCategory, dto.getCategory());
        }
        if (dto.getJobType() != null) {
            wrapper.eq(Job::getJobType, dto.getJobType());
        }
        if (dto.getSalaryMin() != null) {
            wrapper.ge(Job::getSalaryMax, dto.getSalaryMin());
        }
        if (dto.getSalaryMax() != null) {
            wrapper.le(Job::getSalaryMin, dto.getSalaryMax());
        }

        if (StringUtils.hasText(dto.getSortBy())) {
            switch (dto.getSortBy()) {
                case "latest":
                    wrapper.orderByDesc(Job::getPublishTime, Job::getCreateTime);
                    break;
                case "salary":
                    wrapper.orderByDesc(Job::getSalaryMax);
                    break;
                case "response":
                    wrapper.orderByDesc(Job::getApplyCount);
                    break;
                default:
                    wrapper.orderByDesc(Job::getCreateTime);
                    break;
            }
        } else {
            wrapper.orderByDesc(Job::getCreateTime);
        }

        Page<Job> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<Job> result = jobMapper.selectPage(page, wrapper);

        List<JobInfoVO> records = result.getRecords().stream().map(job -> {
            JobInfoVO vo = new JobInfoVO();
            BeanUtils.copyProperties(job, vo);
            Company company = companyMapper.selectById(job.getCompanyId());
            if (company != null) {
                vo.setCompanyName(company.getCompanyName());
            }
            return vo;
        }).collect(Collectors.toList());

        JobListVO listVO = new JobListVO();
        listVO.setTotal(result.getTotal());
        listVO.setPageNum(dto.getPageNum());
        listVO.setPageSize(dto.getPageSize());
        listVO.setTotalPages((int) result.getPages());
        listVO.setRecords(records);

        return Result.success(listVO);
    }

    @Override
    public Result<JobInfoVO> getJobDetail(Long id, Long userId) {
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }

        // 增加浏览次数
        String viewKey = "job:view:" + id;
        redisTemplate.opsForValue().increment(viewKey);

        JobInfoVO vo = new JobInfoVO();
        BeanUtils.copyProperties(job, vo);
        Company company = companyMapper.selectById(job.getCompanyId());
        if (company != null) {
            vo.setCompanyName(company.getCompanyName());
        }

        return Result.success(vo);
    }

    @Override
    public Result<Void> updateJobStatus(Long id, Integer status) {
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }

        job.setStatus(status);
        jobMapper.updateById(job);
        log.info("职位状态更新成功: {} -> {}", id, status);

        return Result.success();
    }

    @Override
    public Result<Map<String, List<String>>> getJobCategories() {
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Job::getStatus, JobEnum.Status.RECRUITING.getCode());
        wrapper.eq(Job::getDeleted, 0);
        wrapper.select(Job::getCategory, Job::getIndustry);
        List<Job> jobs = jobMapper.selectList(wrapper);

        List<String> categories = jobs.stream()
                .map(Job::getCategory)
                .filter(StringUtils::hasText)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> industries = jobs.stream()
                .map(Job::getIndustry)
                .filter(StringUtils::hasText)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        Map<String, List<String>> result = new java.util.HashMap<>();
        result.put("categories", categories);
        result.put("industries", industries);
        return Result.success(result);
    }

    @Override
    public Result<List<JobInfoVO>> getCompanyJobList(Long companyId) {
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Job::getCompanyId, companyId)
                .eq(Job::getDeleted, 0)
                .orderByDesc(Job::getCreateTime);
        List<Job> jobs = jobMapper.selectList(wrapper);

        List<JobInfoVO> records = jobs.stream().map(job -> {
            JobInfoVO vo = new JobInfoVO();
            BeanUtils.copyProperties(job, vo);
            Company company = companyMapper.selectById(job.getCompanyId());
            if (company != null) {
                vo.setCompanyName(company.getCompanyName());
            }
            return vo;
        }).collect(Collectors.toList());

        return Result.success(records);
    }

    @Override
    public Result<JobAdminListVO> getJobAdminList(String keyword, Integer auditStatus, Integer status, String category, String city, Integer page, Integer pageSize) {
        // 参数默认值
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;

        // 构建查询条件
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Job::getDeleted, 0);

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w
                .like(Job::getTitle, keyword)
                .or().like(Job::getDescription, keyword)
            );
        }
        if (auditStatus != null) {
            wrapper.eq(Job::getAuditStatus, auditStatus);
        }
        if (status != null) {
            wrapper.eq(Job::getStatus, status);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Job::getCategory, category);
        }
        if (city != null && !city.isEmpty()) {
            wrapper.eq(Job::getCity, city);
        }

        wrapper.orderByDesc(Job::getCreateTime);

        // 执行分页查询
        Page<Job> pageResult = jobMapper.selectPage(new Page<>(page, pageSize), wrapper);

        // 构建响应
        JobAdminListVO vo = new JobAdminListVO();
        vo.setTotal(pageResult.getTotal());
        vo.setPage(page);
        vo.setPageSize(pageSize);
        vo.setTotalPages((int) Math.ceil((double) pageResult.getTotal() / pageSize));
        vo.setRecords(pageResult.getRecords().stream().map(job -> {
            JobAdminListVO.JobAdminItemVO item = new JobAdminListVO.JobAdminItemVO();
            item.setId(job.getId());
            item.setTitle(job.getTitle());
            item.setCategory(job.getCategory());
            item.setCity(job.getCity());
            item.setSalaryMin(job.getSalaryMin());
            item.setSalaryMax(job.getSalaryMax());
            item.setEducation(job.getEducation());
            item.setJobType(job.getJobType());
            item.setHeadcount(job.getHeadcount());
            item.setStatus(job.getStatus());
            item.setAuditStatus(job.getAuditStatus());
            item.setAuditRemark(job.getAuditRemark());
            item.setViewCount(job.getViewCount());
            item.setApplyCount(job.getApplyCount());
            item.setCreateTime(job.getCreateTime());
            return item;
        }).collect(Collectors.toList()));

        return Result.success(vo);
    }

    @Override
    public Result<JobInfoVO> getJobAdminDetail(Long jobId, Long adminId) {
        Job job = jobMapper.selectById(jobId);
        if (job == null) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }

        JobInfoVO vo = new JobInfoVO();
        BeanUtils.copyProperties(job, vo);

        // 补全企业名称
        if (job.getCompanyId() != null) {
            Company company = companyMapper.selectById(job.getCompanyId());
            if (company != null) {
                vo.setCompanyName(company.getCompanyName());
            }
        }

        return Result.success(vo);
    }

    @Override
    public Result<Void> auditJob(Long jobId, JobAuditDTO dto) {
        Job job = jobMapper.selectById(jobId);
        if (job == null) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }

        // 拒绝时必须填写备注
        if (dto.getAuditStatus() == JobEnum.AuditStatus.REJECTED.getCode()
                && (dto.getRemark() == null || dto.getRemark().isEmpty())) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "拒绝时必须填写审核备注");
        }

        job.setAuditStatus(dto.getAuditStatus());
        job.setAuditTime(LocalDateTime.now());
        job.setAuditRemark(dto.getRemark());

        // 审核通过时，将职位状态设置为招聘中
        if (dto.getAuditStatus() == JobEnum.AuditStatus.APPROVED.getCode()) {
            job.setStatus(JobEnum.Status.RECRUITING.getCode());
        }

        jobMapper.updateById(job);
        log.info("职位审核完成: jobId={}, status={}, remark={}", jobId, dto.getAuditStatus(), dto.getRemark());

        return Result.success();
    }

    @Override
    public Result<Object> getJobCount() {
        Map<String, Object> result = new HashMap<>();

        // 总职位数
        LambdaQueryWrapper<Job> totalWrapper = new LambdaQueryWrapper<>();
        totalWrapper.eq(Job::getDeleted, 0);
        long total = jobMapper.selectCount(totalWrapper);
        result.put("total", total);

        // 按状态统计
        Map<String, Long> byStatus = new HashMap<>();
        for (JobEnum.Status status : JobEnum.Status.values()) {
            LambdaQueryWrapper<Job> statusWrapper = new LambdaQueryWrapper<>();
            statusWrapper.eq(Job::getDeleted, 0).eq(Job::getStatus, status.getCode());
            byStatus.put(status.name().toLowerCase(), jobMapper.selectCount(statusWrapper));
        }
        result.put("byStatus", byStatus);

        // 按审核状态统计
        Map<String, Long> byAuditStatus = new HashMap<>();
        for (JobEnum.AuditStatus auditStatus : JobEnum.AuditStatus.values()) {
            LambdaQueryWrapper<Job> auditWrapper = new LambdaQueryWrapper<>();
            auditWrapper.eq(Job::getDeleted, 0).eq(Job::getAuditStatus, auditStatus.getCode());
            byAuditStatus.put(auditStatus.name().toLowerCase(), jobMapper.selectCount(auditWrapper));
        }
        result.put("byAuditStatus", byAuditStatus);

        return Result.success(result);
    }
}
