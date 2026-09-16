package com.campus.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.common.enums.CompanyEnum;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import com.campus.company.dto.CompanyAuditDTO;
import com.campus.company.dto.CompanyAuditHandleDTO;
import com.campus.company.entity.Company;
import com.campus.company.mapper.CompanyMapper;
import com.campus.company.service.CompanyService;
import com.campus.company.vo.CompanyInfoVO;
import com.campus.company.vo.CompanyListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    @Override
    public Result<CompanyListVO> getCompanyList(String keyword, Integer auditStatus, String industry, Integer page, Integer pageSize) {
        // 参数默认值
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;

        // 构建查询条件
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getDeleted, 0);

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w
                .like(Company::getCompanyName, keyword)
                .or().like(Company::getLicenseUrl, keyword)
            );
        }
        if (auditStatus != null) {
            wrapper.eq(Company::getAuditStatus, auditStatus);
        }
        if (industry != null && !industry.isEmpty()) {
            wrapper.eq(Company::getIndustry, industry);
        }

        wrapper.orderByDesc(Company::getCreateTime);

        // 执行分页查询
        Page<Company> pageResult = companyMapper.selectPage(new Page<>(page, pageSize), wrapper);

        // 构建响应
        CompanyListVO vo = new CompanyListVO();
        vo.setTotal(pageResult.getTotal());
        vo.setPage(page);
        vo.setPageSize(pageSize);
        vo.setTotalPages((int) Math.ceil((double) pageResult.getTotal() / pageSize));
        vo.setRecords(pageResult.getRecords().stream().map(company -> {
            CompanyListVO.CompanyItemVO item = new CompanyListVO.CompanyItemVO();
            item.setId(company.getId());
            item.setUserId(company.getUserId());
            item.setCompanyName(company.getCompanyName());
            item.setIndustry(company.getIndustry());
            item.setScale(company.getScale());
            item.setLicenseUrl(company.getLicenseUrl());
            item.setAuditStatus(company.getAuditStatus());
            item.setAuditStatusLabel(getAuditStatusLabel(company.getAuditStatus()));
            item.setAuditRemark(company.getAuditRemark());
            item.setCreateTime(company.getCreateTime());
            return item;
        }).collect(Collectors.toList()));

        return Result.success(vo);
    }

    @Override
    public Result<Void> approveCompany(Long companyId) {
        Company company = companyMapper.selectById(companyId);
        if (company == null) {
            throw new BusinessException(ResultCode.COMPANY_NOT_FOUND);
        }

        company.setAuditStatus(CompanyEnum.AuditStatus.APPROVED.getCode());
        company.setAuditTime(LocalDateTime.now());
        companyMapper.updateById(company);

        log.info("企业审核通过: companyId={}", companyId);
        return Result.success();
    }

    @Override
    public Result<Void> rejectCompany(Long companyId, CompanyAuditHandleDTO dto) {
        Company company = companyMapper.selectById(companyId);
        if (company == null) {
            throw new BusinessException(ResultCode.COMPANY_NOT_FOUND);
        }

        company.setAuditStatus(CompanyEnum.AuditStatus.REJECTED.getCode());
        company.setAuditTime(LocalDateTime.now());
        company.setAuditRemark(dto.getRemark());
        companyMapper.updateById(company);

        log.info("企业审核拒绝: companyId={}, reason={}", companyId, dto.getRemark());
        return Result.success();
    }

    private String getAuditStatusLabel(Integer auditStatus) {
        if (auditStatus == null) return "未知";
        switch (auditStatus) {
            case 0: return "待审核";
            case 1: return "已通过";
            case 2: return "已拒绝";
            default: return "未知";
        }
    }
}
