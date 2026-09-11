package com.campus.company.controller;

import com.campus.common.result.Result;
import com.campus.company.dto.CompanyAuditDTO;
import com.campus.company.service.CompanyService;
import com.campus.company.vo.CompanyInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 企业控制器
 */
@Api(tags = "企业管理")
@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @ApiOperation("企业认证（提交认证申请）")
    @PostMapping("/audit")
    public Result<Void> audit(@RequestHeader("X-User-Id") Long userId,
                              @RequestBody @Valid CompanyAuditDTO dto) {
        return companyService.audit(userId, dto);
    }

    @ApiOperation("获取企业信息")
    @GetMapping("/info")
    public Result<CompanyInfoVO> getCompanyInfo(@RequestHeader("X-User-Id") Long userId) {
        return companyService.getCompanyInfo(userId);
    }

    @ApiOperation("更新企业信息")
    @PutMapping("/info")
    public Result<Void> updateCompanyInfo(@RequestHeader("X-User-Id") Long userId,
                                          @RequestBody CompanyInfoVO vo) {
        return companyService.updateCompanyInfo(userId, vo);
    }
}
