package com.campus.recommend.feign;

import com.campus.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
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
     * 获取职位列表（分页）
     */
    @GetMapping("/api/job/list")
    Result<Map<String, Object>> getJobList(Map<String, Object> params);
}
