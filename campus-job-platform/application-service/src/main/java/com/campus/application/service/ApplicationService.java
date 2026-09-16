package com.campus.application.service;

import com.campus.application.dto.ApplicationHandleDTO;
import com.campus.application.dto.ApplicationSubmitDTO;
import com.campus.application.vo.ApplicationInfoVO;
import com.campus.application.vo.ApplicationListVO;
import com.campus.application.vo.ResumeInfoVO;
import com.campus.common.result.Result;

/**
 * 投递服务接口
 */
public interface ApplicationService {

    /**
     * 提交投递
     */
    Result<Long> submitApplication(Long userId, ApplicationSubmitDTO dto);

    /**
     * 获取投递列表（学生）
     */
    Result<ApplicationListVO> getApplicationList(Long userId, Integer status, Integer page, Integer size);

    /**
     * 获取投递详情
     */
    Result<ApplicationInfoVO> getApplicationDetail(Long userId, Long id);

    /**
     * 处理投递（企业HR）
     */
    Result<Void> handleApplication(Long userId, Long id, ApplicationHandleDTO dto);

    /**
     * 获取收到的投递列表（企业）
     */
    Result<ApplicationListVO> getReceivedApplications(Long userId, Long companyId, Integer status, Integer page, Integer size);

    /**
     * 企业查看候选人简历（按投递记录取简历，手机号脱敏，仅HR/管理员可调）
     */
    Result<ResumeInfoVO> getCandidateResume(Long userId, Integer role, Long applicationId);
}
