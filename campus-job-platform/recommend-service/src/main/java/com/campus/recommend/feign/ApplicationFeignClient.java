package com.campus.recommend.feign;

import com.campus.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

/**
 * 投递服务Feign客户端（获取简历）
 */
@FeignClient(name = "application-service")
public interface ApplicationFeignClient {

    /**
     * 获取用户简历列表
     */
    @GetMapping("/api/resume/list")
    Result<List<Map<String, Object>>> getResumeList(
            @RequestHeader("X-User-Id") Long userId);
}
