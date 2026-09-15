package com.campus.job.controller;

import com.campus.common.result.Result;
import com.campus.job.dto.JobAuditDTO;
import com.campus.job.dto.JobPublishDTO;
import com.campus.job.dto.JobSearchDTO;
import com.campus.job.service.JobService;
import com.campus.job.vo.JobAdminListVO;
import com.campus.job.vo.JobInfoVO;
import com.campus.job.vo.JobListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 职位控制器
 */
@Api(tags = "职位管理")
@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @ApiOperation("发布职位")
    @PostMapping("/publish")
    public Result<Void> publishJob(@RequestBody @Valid JobPublishDTO dto) {
        return jobService.publishJob(dto);
    }

    @ApiOperation("更新职位")
    @PutMapping("/update")
    public Result<Void> updateJob(@RequestParam Long id, @RequestBody @Valid JobPublishDTO dto) {
        return jobService.updateJob(id, dto);
    }

    @ApiOperation("搜索职位")
    @GetMapping("/search")
    public Result<JobListVO> searchJobs(JobSearchDTO dto) {
        return jobService.searchJobs(dto);
    }

    @ApiOperation("获取职位详情")
    @GetMapping("/detail/{id}")
    public Result<JobInfoVO> getJobDetail(@PathVariable Long id,
                                          @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        return jobService.getJobDetail(id, userId);
    }

    @ApiOperation("更新职位状态")
    @PutMapping("/status/{id}")
    public Result<Void> updateJobStatus(@PathVariable Long id, @RequestParam Integer status) {
        return jobService.updateJobStatus(id, status);
    }

    @ApiOperation("管理员获取职位列表")
    @GetMapping("/admin/list")
    public Result<JobAdminListVO> getJobAdminList(
            @RequestHeader("X-User-Id") Long adminId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer auditStatus,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return jobService.getJobAdminList(keyword, auditStatus, status, category, city, page, pageSize);
    }

    @ApiOperation("管理员审核职位")
    @PutMapping("/audit/{id}")
    public Result<Void> auditJob(
            @RequestHeader("X-User-Id") Long adminId,
            @PathVariable Long id,
            @RequestBody @Valid JobAuditDTO dto) {
        return jobService.auditJob(id, dto);
    }

    @ApiOperation("管理员获取职位统计数据")
    @GetMapping("/count")
    public Result<Object> getJobCount(@RequestHeader("X-User-Id") Long adminId) {
        return jobService.getJobCount();
    }
}
