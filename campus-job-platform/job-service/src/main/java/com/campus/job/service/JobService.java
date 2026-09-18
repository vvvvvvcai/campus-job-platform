package com.campus.job.service;

import com.campus.common.result.Result;
import com.campus.job.dto.JobAuditDTO;
import com.campus.job.dto.JobPublishDTO;
import com.campus.job.dto.JobSearchDTO;
import com.campus.job.vo.JobAdminListVO;
import com.campus.job.vo.JobInfoVO;
import com.campus.job.vo.JobListVO;

import java.util.List;
import java.util.Map;

/**
 * 职位服务接口
 */
public interface JobService {

    /**
     * 发布职位
     */
    Result<Void> publishJob(JobPublishDTO dto);

    /**
     * 更新职位
     */
    Result<Void> updateJob(Long id, JobPublishDTO dto);

    /**
     * 搜索职位
     */
    Result<JobListVO> searchJobs(JobSearchDTO dto);

    /**
     * 获取职位详情
     */
    Result<JobInfoVO> getJobDetail(Long id, Long userId);

    /**
     * 更新职位状态
     */
    Result<Void> updateJobStatus(Long id, Integer status);

    /**
     * 获取所有职位类别和行业
     */
    Result<Map<String, List<String>>> getJobCategories();

    /**
     * 获取企业发布的职位列表（含已关闭/待审核，用于企业端职位管理）
     */
    Result<List<JobInfoVO>> getCompanyJobList(Long companyId);

    /**
     * 管理员：获取职位列表
     */
    Result<JobAdminListVO> getJobAdminList(String keyword, Integer auditStatus, Integer status, String category, String city, Integer page, Integer pageSize);

    /**
     * 管理员：获取职位详情（含企业名称）
     */
    Result<JobInfoVO> getJobAdminDetail(Long jobId, Long adminId);

    /**
     * 管理员：审核职位
     */
    Result<Void> auditJob(Long jobId, JobAuditDTO dto);

    /**
     * 管理员：获取职位统计数据
     */
    Result<Object> getJobCount();

    /**
     * 管理员：获取近7天职位发布趋势
     */
    Result<Object> getJobTrend();
}
