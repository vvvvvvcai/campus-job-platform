package com.campus.recommend.feign;

import com.campus.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 职位服务Feign客户端
 */
@FeignClient(name = "job-service")
public interface JobFeignClient {

    /**
     * 获取职位详情
     */
    @GetMapping("/api/job/detail/{jobId}")
    Result<Map<String, Object>> getJobDetail(@PathVariable("jobId") Long jobId);

    /**
     * 搜索职位列表（分页）
     * job-service内部自动过滤status=招聘中、deleted=0
     */
    @GetMapping("/api/job/search")
    Result<Map<String, Object>> searchJobs(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") Integer pageSize);
}
