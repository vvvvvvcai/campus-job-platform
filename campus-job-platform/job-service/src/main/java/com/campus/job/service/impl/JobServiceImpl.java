package com.campus.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.common.enums.JobEnum;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import com.campus.job.dto.JobPublishDTO;
import com.campus.job.dto.JobSearchDTO;
import com.campus.job.entity.Company;
import com.campus.job.entity.Job;
import com.campus.job.mapper.CompanyMapper;
import com.campus.job.mapper.JobMapper;
import com.campus.job.service.JobService;
import com.campus.job.vo.JobInfoVO;
import com.campus.job.vo.JobListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
}
