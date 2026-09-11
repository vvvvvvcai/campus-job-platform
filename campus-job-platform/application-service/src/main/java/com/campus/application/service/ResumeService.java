package com.campus.application.service;

import com.campus.application.dto.ResumeSaveDTO;
import com.campus.application.vo.ResumeInfoVO;
import com.campus.common.result.Result;

import java.util.List;

/**
 * 简历服务接口
 */
public interface ResumeService {

    /**
     * 保存简历
     */
    Result<Long> saveResume(Long userId, ResumeSaveDTO dto);

    /**
     * 更新简历
     */
    Result<Void> updateResume(Long userId, Long id, ResumeSaveDTO dto);

    /**
     * 获取简历详情
     */
    Result<ResumeInfoVO> getResumeInfo(Long userId, Long id);

    /**
     * 删除简历
     */
    Result<Void> deleteResume(Long userId, Long id);

    /**
     * 获取简历列表
     */
    Result<List<ResumeInfoVO>> getResumeList(Long userId);

    /**
     * 设置默认简历
     */
    Result<Void> setDefaultResume(Long userId, Long id);
}
