package com.campus.company.service;

import com.campus.common.result.Result;
import com.campus.company.dto.CompanyAuditDTO;
import com.campus.company.vo.CompanyInfoVO;

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
}
