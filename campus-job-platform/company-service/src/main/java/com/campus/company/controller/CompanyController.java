package com.campus.company.controller;

import com.campus.common.result.Result;
import com.campus.company.dto.CompanyAuditDTO;
import com.campus.company.dto.CompanyAuditHandleDTO;
import com.campus.company.service.CompanyService;
import com.campus.company.vo.CompanyInfoVO;
import com.campus.company.vo.CompanyListVO;
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

    @ApiOperation("管理员获取企业列表")
    @GetMapping("/list")
    public Result<CompanyListVO> getCompanyList(
            @RequestHeader("X-User-Id") Long adminId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer auditStatus,
            @RequestParam(required = false) String industry,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return companyService.getCompanyList(keyword, auditStatus, industry, page, pageSize);
    }

    @ApiOperation("管理员通过企业审核")
    @PutMapping("/audit/approve/{id}")
    public Result<Void> approveCompany(
            @RequestHeader("X-User-Id") Long adminId,
            @PathVariable Long id) {
        return companyService.approveCompany(id);
    }

    @ApiOperation("管理员拒绝企业审核")
    @PutMapping("/audit/reject/{id}")
    public Result<Void> rejectCompany(
            @RequestHeader("X-User-Id") Long adminId,
            @PathVariable Long id,
            @RequestBody CompanyAuditHandleDTO dto) {
        return companyService.rejectCompany(id, dto);
    }
}
