package com.campus.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.common.enums.CompanyEnum;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import com.campus.company.dto.CompanyAuditDTO;
import com.campus.company.entity.Company;
import com.campus.company.mapper.CompanyMapper;
import com.campus.company.service.CompanyService;
import com.campus.company.vo.CompanyInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 企业服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;

    @Override
    public Result<Void> audit(Long userId, CompanyAuditDTO dto) {
        // 查询该用户是否已有企业信息
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getUserId, userId);
        Company company = companyMapper.selectOne(wrapper);

        if (company == null) {
            // 首次认证，创建企业记录
            company = new Company();
            company.setUserId(userId);
            company.setCompanyName(dto.getCompanyName());
            company.setLicenseUrl(dto.getLicenseUrl());
            company.setDescription(dto.getDescription());
            company.setAuditStatus(CompanyEnum.AuditStatus.PENDING.getCode());
            companyMapper.insert(company);
        } else {
            // 已有记录，更新认证信息并重置审核状态
            company.setCompanyName(dto.getCompanyName());
            company.setLicenseUrl(dto.getLicenseUrl());
            company.setDescription(dto.getDescription());
            company.setAuditStatus(CompanyEnum.AuditStatus.PENDING.getCode());
            company.setAuditTime(null);
            company.setAuditRemark(null);
            companyMapper.updateById(company);
        }

        log.info("企业认证申请已提交: userId={}", userId);
        return Result.success();
    }

    @Override
    public Result<CompanyInfoVO> getCompanyInfo(Long userId) {
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getUserId, userId);
        Company company = companyMapper.selectOne(wrapper);

        if (company == null) {
            throw new BusinessException(ResultCode.COMPANY_NOT_FOUND);
        }

        CompanyInfoVO vo = new CompanyInfoVO();
        BeanUtils.copyProperties(company, vo);
        return Result.success(vo);
    }

    @Override
    public Result<Void> updateCompanyInfo(Long userId, CompanyInfoVO vo) {
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getUserId, userId);
        Company company = companyMapper.selectOne(wrapper);

        if (company == null) {
            throw new BusinessException(ResultCode.COMPANY_NOT_FOUND);
        }

        // 更新允许的字段
        company.setCompanyName(vo.getCompanyName());
        company.setIndustry(vo.getIndustry());
        company.setScale(vo.getScale());
        company.setNature(vo.getNature());
        company.setAddress(vo.getAddress());
        company.setWebsite(vo.getWebsite());
        company.setLogo(vo.getLogo());
        company.setDescription(vo.getDescription());

        companyMapper.updateById(company);
        return Result.success();
    }
}
