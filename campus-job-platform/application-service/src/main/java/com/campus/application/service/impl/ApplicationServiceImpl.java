package com.campus.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.application.dto.ApplicationHandleDTO;
import com.campus.application.dto.ApplicationSubmitDTO;
import com.campus.application.entity.Application;
import com.campus.application.entity.Resume;
import com.campus.application.mapper.ApplicationMapper;
import com.campus.application.mapper.ResumeMapper;
import com.campus.application.service.ApplicationService;
import com.campus.application.service.NotificationService;
import com.campus.application.vo.ApplicationInfoVO;
import com.campus.application.vo.ApplicationListVO;
import com.campus.application.vo.ResumeInfoVO;
import com.campus.common.enums.ApplicationEnum;
import com.campus.common.utils.PhoneUtils;
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
    private final NotificationService notificationService;

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
        Page<ApplicationInfoVO> pageParam = new Page<>(page, size);
        IPage<ApplicationInfoVO> result = applicationMapper.selectApplicationList(pageParam, userId, null, status);

        ApplicationListVO listVO = new ApplicationListVO();
        listVO.setRecords(result.getRecords());
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

        // 根据状态变更创建通知
        createNotificationByStatus(application, dto);

        return Result.success();
    }

    /**
     * 根据投递状态变更创建通知
     */
    private void createNotificationByStatus(Application application, ApplicationHandleDTO dto) {
        Long studentId = application.getUserId();
        Integer newStatus = dto.getStatus();

        if (newStatus == null) return;

        switch (newStatus) {
            case 2: // 面试邀请
                String interviewInfo = buildInterviewInfo(dto);
                notificationService.createNotification(
                        studentId,
                        "面试邀请",
                        "恭喜！您投递的职位已收到面试邀请。" + interviewInfo,
                        2, // 面试通知
                        application.getId()
                );
                break;
            case 3: // 不合适
                String rejectReason = dto.getHrRemark() != null ? "，原因：" + dto.getHrRemark() : "";
                notificationService.createNotification(
                        studentId,
                        "投递结果通知",
                        "很抱歉，您投递的职位暂未通过筛选" + rejectReason,
                        1, // 投递通知
                        application.getId()
                );
                break;
            case 4: // 已录用
                notificationService.createNotification(
                        studentId,
                        "录用通知",
                        "恭喜！您投递的职位已被录用，请留意后续入职安排。",
                        1, // 投递通知
                        application.getId()
                );
                break;
            default:
                break;
        }
    }

    /**
     * 构建面试信息字符串
     */
    private String buildInterviewInfo(ApplicationHandleDTO dto) {
        StringBuilder sb = new StringBuilder();
        if (dto.getInterviewTime() != null) {
            sb.append("面试时间：").append(dto.getInterviewTime());
        }
        if (dto.getInterviewAddress() != null && !dto.getInterviewAddress().isEmpty()) {
            sb.append("，面试地点：").append(dto.getInterviewAddress());
        }
        if (dto.getInterviewContact() != null && !dto.getInterviewContact().isEmpty()) {
            sb.append("，联系人：").append(dto.getInterviewContact());
        }
        if (dto.getInterviewContactPhone() != null && !dto.getInterviewContactPhone().isEmpty()) {
            sb.append("（").append(dto.getInterviewContactPhone()).append("）");
        }
        return sb.toString();
    }

    @Override
    public Result<ApplicationListVO> getReceivedApplications(Long userId, Long companyId, Integer status, Integer page, Integer size) {
        Page<ApplicationInfoVO> pageParam = new Page<>(page, size);
        IPage<ApplicationInfoVO> result = applicationMapper.selectApplicationList(pageParam, null, companyId, status);

        ApplicationListVO listVO = new ApplicationListVO();
        listVO.setRecords(result.getRecords());
        listVO.setTotal(result.getTotal());
        listVO.setPage((long) page);
        listVO.setSize((long) size);
        listVO.setPages(result.getPages());

        return Result.success(listVO);
    }

    @Override
    public Result<ResumeInfoVO> getCandidateResume(Long userId, Integer role, Long applicationId) {
        // 仅企业HR(1)或管理员(2)可查看候选人简历
        if (role == null || (role != 1 && role != 2)) {
            throw new BusinessException(ResultCode.FAIL.getCode(), "无权查看候选人简历");
        }

        Application application = applicationMapper.selectById(applicationId);
        if (application == null) {
            throw new BusinessException(ResultCode.APPLICATION_NOT_FOUND);
        }

        Resume resume = resumeMapper.selectById(application.getResumeId());
        if (resume == null) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }

        ResumeInfoVO vo = new ResumeInfoVO();
        BeanUtils.copyProperties(resume, vo);
        // 候选人隐私保护：手机号脱敏
        vo.setPhone(PhoneUtils.desensitize(resume.getPhone()));
        log.info("企业查看候选人简历: applicationId={}, resumeId={}, byUserId={}", applicationId, resume.getId(), userId);

        return Result.success(vo);
    }
}
