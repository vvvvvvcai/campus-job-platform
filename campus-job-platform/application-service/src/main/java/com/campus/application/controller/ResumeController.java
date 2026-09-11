package com.campus.application.controller;

import com.campus.application.dto.ResumeSaveDTO;
import com.campus.application.service.ResumeService;
import com.campus.application.vo.ResumeInfoVO;
import com.campus.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 简历控制器
 */
@Api(tags = "简历管理")
@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @ApiOperation("保存简历")
    @PostMapping
    public Result<Long> saveResume(@RequestHeader("X-User-Id") Long userId,
                                   @RequestBody @Valid ResumeSaveDTO dto) {
        return resumeService.saveResume(userId, dto);
    }

    @ApiOperation("更新简历")
    @PutMapping("/{id}")
    public Result<Void> updateResume(@RequestHeader("X-User-Id") Long userId,
                                     @PathVariable Long id,
                                     @RequestBody @Valid ResumeSaveDTO dto) {
        return resumeService.updateResume(userId, id, dto);
    }

    @ApiOperation("获取简历详情")
    @GetMapping("/{id}")
    public Result<ResumeInfoVO> getResumeInfo(@RequestHeader("X-User-Id") Long userId,
                                              @PathVariable Long id) {
        return resumeService.getResumeInfo(userId, id);
    }

    @ApiOperation("删除简历")
    @DeleteMapping("/{id}")
    public Result<Void> deleteResume(@RequestHeader("X-User-Id") Long userId,
                                     @PathVariable Long id) {
        return resumeService.deleteResume(userId, id);
    }

    @ApiOperation("获取简历列表")
    @GetMapping("/list")
    public Result<List<ResumeInfoVO>> getResumeList(@RequestHeader("X-User-Id") Long userId) {
        return resumeService.getResumeList(userId);
    }

    @ApiOperation("设置默认简历")
    @PutMapping("/default/{id}")
    public Result<Void> setDefaultResume(@RequestHeader("X-User-Id") Long userId,
                                         @PathVariable Long id) {
        return resumeService.setDefaultResume(userId, id);
    }
}
