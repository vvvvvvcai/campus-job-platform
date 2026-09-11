package com.campus.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.application.dto.ResumeSaveDTO;
import com.campus.application.entity.Resume;
import com.campus.application.mapper.ResumeMapper;
import com.campus.application.service.ResumeService;
import com.campus.application.vo.ResumeInfoVO;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 简历服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeMapper resumeMapper;

    @Override
    @Transactional
    public Result<Long> saveResume(Long userId, ResumeSaveDTO dto) {
        Resume resume = new Resume();
        BeanUtils.copyProperties(dto, resume);
        resume.setUserId(userId);
        resume.setStatus(0);
        resume.setIsDefault(0);

        // 如果是第一份简历，设置为默认
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resume::getUserId, userId);
        if (resumeMapper.selectCount(wrapper) == 0) {
            resume.setIsDefault(1);
        }

        resumeMapper.insert(resume);
        log.info("简历保存成功: userId={}, resumeId={}", userId, resume.getId());

        return Result.success(resume.getId());
    }

    @Override
    @Transactional
    public Result<Void> updateResume(Long userId, Long id, ResumeSaveDTO dto) {
        Resume resume = resumeMapper.selectById(id);
        if (resume == null || !resume.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }

        BeanUtils.copyProperties(dto, resume);
        resume.setId(id);
        resumeMapper.updateById(resume);

        log.info("简历更新成功: userId={}, resumeId={}", userId, id);
        return Result.success();
    }

    @Override
    public Result<ResumeInfoVO> getResumeInfo(Long userId, Long id) {
        Resume resume = resumeMapper.selectById(id);
        if (resume == null || !resume.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }

        ResumeInfoVO vo = new ResumeInfoVO();
        BeanUtils.copyProperties(resume, vo);

        return Result.success(vo);
    }

    @Override
    @Transactional
    public Result<Void> deleteResume(Long userId, Long id) {
        Resume resume = resumeMapper.selectById(id);
        if (resume == null || !resume.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }

        boolean isDefault = resume.getIsDefault() == 1;
        resumeMapper.deleteById(id);

        // 如果删除的是默认简历，将最早的简历设为默认
        if (isDefault) {
            LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Resume::getUserId, userId)
                    .orderByAsc(Resume::getCreateTime)
                    .last("LIMIT 1");
            Resume earliestResume = resumeMapper.selectOne(wrapper);
            if (earliestResume != null) {
                earliestResume.setIsDefault(1);
                resumeMapper.updateById(earliestResume);
            }
        }

        log.info("简历删除成功: userId={}, resumeId={}", userId, id);
        return Result.success();
    }

    @Override
    public Result<List<ResumeInfoVO>> getResumeList(Long userId) {
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resume::getUserId, userId)
                .orderByDesc(Resume::getIsDefault)
                .orderByDesc(Resume::getCreateTime);

        List<Resume> resumes = resumeMapper.selectList(wrapper);
        List<ResumeInfoVO> voList = resumes.stream().map(resume -> {
            ResumeInfoVO vo = new ResumeInfoVO();
            BeanUtils.copyProperties(resume, vo);
            return vo;
        }).collect(Collectors.toList());

        return Result.success(voList);
    }

    @Override
    @Transactional
    public Result<Void> setDefaultResume(Long userId, Long id) {
        Resume resume = resumeMapper.selectById(id);
        if (resume == null || !resume.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.RESUME_NOT_FOUND);
        }

        // 取消当前默认简历
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resume::getUserId, userId)
                .eq(Resume::getIsDefault, 1);
        Resume currentDefault = resumeMapper.selectOne(wrapper);
        if (currentDefault != null) {
            currentDefault.setIsDefault(0);
            resumeMapper.updateById(currentDefault);
        }

        // 设置新默认简历
        resume.setIsDefault(1);
        resumeMapper.updateById(resume);

        log.info("默认简历设置成功: userId={}, resumeId={}", userId, id);
        return Result.success();
    }
}
