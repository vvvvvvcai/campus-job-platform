package com.campus.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.application.dto.ApplicationHandleDTO;
import com.campus.application.dto.ApplicationSubmitDTO;
import com.campus.application.entity.Application;
import com.campus.application.entity.Resume;
import com.campus.application.mapper.ApplicationMapper;
import com.campus.application.mapper.ResumeMapper;
import com.campus.application.service.ApplicationService;
import com.campus.application.vo.ApplicationInfoVO;
import com.campus.application.vo.ApplicationListVO;
import com.campus.common.enums.ApplicationEnum;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 投递服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper applicationMapper;
    private final ResumeMapper resumeMapper;

    @Override
    @Transactional
    public Result<Long> submitApplication(Long userId, ApplicationSubmitDTO dto) {
        // 检查简历是否存在
        Resume resume = resumeMapper.selectById(dto.getResumeId());
        if (resume == null || !resume.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }

        // 检查是否已投递过该职位
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getUserId, userId)
                .eq(Application::getJobId, dto.getJobId());
        if (applicationMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.APPLICATION_EXISTS);
        }

        // 创建投递记录
        Application application = new Application();
        application.setUserId(userId);
        application.setJobId(dto.getJobId());
        application.setCompanyId(dto.getCompanyId());
        application.setResumeId(dto.getResumeId());
        application.setStatus(ApplicationEnum.Status.PENDING.getCode());

        applicationMapper.insert(application);
        log.info("投递成功: userId={}, jobId={}, applicationId={}", userId, dto.getJobId(), application.getId());

        return Result.success(application.getId());
    }

    @Override
    public Result<ApplicationListVO> getApplicationList(Long userId, Integer status, Integer page, Integer size) {
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getUserId, userId);
        if (status != null) {
            wrapper.eq(Application::getStatus, status);
        }
        wrapper.orderByDesc(Application::getCreateTime);

        Page<Application> pageParam = new Page<>(page, size);
        Page<Application> result = applicationMapper.selectPage(pageParam, wrapper);

        ApplicationListVO listVO = new ApplicationListVO();
        listVO.setRecords(null); // 简化处理，实际应转换为VO
        listVO.setTotal(result.getTotal());
        listVO.setPage((long) page);
        listVO.setSize((long) size);
        listVO.setPages(result.getPages());

        return Result.success(listVO);
    }

    @Override
    public Result<ApplicationInfoVO> getApplicationDetail(Long userId, Long id) {
        Application application = applicationMapper.selectById(id);
        if (application == null || !application.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.APPLICATION_NOT_FOUND);
        }

        ApplicationInfoVO vo = new ApplicationInfoVO();
        BeanUtils.copyProperties(application, vo);

        // 查询简历标题
        Resume resume = resumeMapper.selectById(application.getResumeId());
        if (resume != null) {
            vo.setResumeTitle(resume.getTitle());
        }

        return Result.success(vo);
    }

    @Override
    @Transactional
    public Result<Void> handleApplication(Long userId, Long id, ApplicationHandleDTO dto) {
        Application application = applicationMapper.selectById(id);
        if (application == null) {
            throw new BusinessException(ResultCode.APPLICATION_NOT_FOUND);
        }

        // 更新投递状态
        application.setStatus(dto.getStatus());
        application.setHrRemark(dto.getHrRemark());
        application.setInterviewTime(dto.getInterviewTime());
        application.setInterviewAddress(dto.getInterviewAddress());
        application.setInterviewContact(dto.getInterviewContact());
        application.setInterviewContactPhone(dto.getInterviewContactPhone());

        applicationMapper.updateById(application);
        log.info("投递处理成功: applicationId={}, status={}", id, dto.getStatus());

        return Result.success();
    }

    @Override
    public Result<ApplicationListVO> getReceivedApplications(Long userId, Long companyId, Integer status, Integer page, Integer size) {
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getCompanyId, companyId);
        if (status != null) {
            wrapper.eq(Application::getStatus, status);
        }
        wrapper.orderByDesc(Application::getCreateTime);

        Page<Application> pageParam = new Page<>(page, size);
        Page<Application> result = applicationMapper.selectPage(pageParam, wrapper);

        ApplicationListVO listVO = new ApplicationListVO();
        listVO.setRecords(null); // 简化处理，实际应转换为VO
        listVO.setTotal(result.getTotal());
        listVO.setPage((long) page);
        listVO.setSize((long) size);
        listVO.setPages(result.getPages());

        return Result.success(listVO);
    }
}
