package com.campus.company.service;

import com.campus.common.result.Result;
import com.campus.company.dto.CompanyAuditDTO;
import com.campus.company.dto.CompanyAuditHandleDTO;
import com.campus.company.vo.CompanyInfoVO;
import com.campus.company.vo.CompanyListVO;

/**
 * 企业服务接口
 */
public interface CompanyService {

    /**
     * 企业认证（提交认证申请）
     */
    Result<Void> audit(Long userId, CompanyAuditDTO dto);

    /**
     * 获取企业信息
     */
    Result<CompanyInfoVO> getCompanyInfo(Long userId);

    /**
     * 更新企业信息
     */
    Result<Void> updateCompanyInfo(Long userId, CompanyInfoVO vo);

    /**
     * 管理员：获取企业列表
     */
    Result<CompanyListVO> getCompanyList(String keyword, Integer auditStatus, String industry, Integer page, Integer pageSize);

    /**
     * 管理员：获取企业详情（含HR信息）
     */
    Result<CompanyInfoVO> getCompanyAdminDetail(Long companyId);

    /**
     * 管理员：通过企业审核
     */
    Result<Void> approveCompany(Long companyId);

    /**
     * 管理员：拒绝企业审核
     */
    Result<Void> rejectCompany(Long companyId, CompanyAuditHandleDTO dto);
}
