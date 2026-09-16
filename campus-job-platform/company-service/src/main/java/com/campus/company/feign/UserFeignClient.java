package com.campus.company.feign;

import com.campus.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * 用户服务Feign客户端
 */
@FeignClient(name = "user-service")
public interface UserFeignClient {

    /**
     * 获取用户信息（用于查询企业HR联系方式）
     */
    @GetMapping("/api/user/info")
    Result<Map<String, Object>> getUserInfo(@RequestHeader("X-User-Id") Long userId);
}