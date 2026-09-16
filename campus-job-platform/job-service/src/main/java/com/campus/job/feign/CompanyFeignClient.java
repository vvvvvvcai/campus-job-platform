package com.campus.job.feign;

import com.campus.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * 企业服务Feign客户端
 */
@FeignClient(name = "company-service")
public interface CompanyFeignClient {

    /**
     * 管理员获取企业详情（用于补全企业名称）
     */
    @GetMapping("/api/company/admin/detail/{companyId}")
    Result<Map<String, Object>> getCompanyAdminDetail(@PathVariable("companyId") Long companyId,
                                                      @RequestHeader("X-User-Id") Long adminId);
}