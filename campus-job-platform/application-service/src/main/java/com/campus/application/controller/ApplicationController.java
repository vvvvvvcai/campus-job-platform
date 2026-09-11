package com.campus.application.controller;

import com.campus.application.dto.ApplicationHandleDTO;
import com.campus.application.dto.ApplicationSubmitDTO;
import com.campus.application.service.ApplicationService;
import com.campus.application.vo.ApplicationInfoVO;
import com.campus.application.vo.ApplicationListVO;
import com.campus.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 投递控制器
 */
@Api(tags = "投递管理")
@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @ApiOperation("提交投递")
    @PostMapping("/submit")
    public Result<Long> submitApplication(@RequestHeader("X-User-Id") Long userId,
                                          @RequestBody @Valid ApplicationSubmitDTO dto) {
        return applicationService.submitApplication(userId, dto);
    }

    @ApiOperation("获取投递列表")
    @GetMapping("/list")
    public Result<ApplicationListVO> getApplicationList(
            @RequestHeader("X-User-Id") Long userId,
            @ApiParam("投递状态") @RequestParam(required = false) Integer status,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return applicationService.getApplicationList(userId, status, page, size);
    }

    @ApiOperation("获取投递详情")
    @GetMapping("/detail/{id}")
    public Result<ApplicationInfoVO> getApplicationDetail(@RequestHeader("X-User-Id") Long userId,
                                                          @PathVariable Long id) {
        return applicationService.getApplicationDetail(userId, id);
    }

    @ApiOperation("处理投递")
    @PutMapping("/handle/{id}")
    public Result<Void> handleApplication(@RequestHeader("X-User-Id") Long userId,
                                          @PathVariable Long id,
                                          @RequestBody @Valid ApplicationHandleDTO dto) {
        return applicationService.handleApplication(userId, id, dto);
    }

    @ApiOperation("获取收到的投递列表（企业）")
    @GetMapping("/received")
    public Result<ApplicationListVO> getReceivedApplications(
            @RequestHeader("X-User-Id") Long userId,
            @ApiParam("企业ID") @RequestParam Long companyId,
            @ApiParam("投递状态") @RequestParam(required = false) Integer status,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return applicationService.getReceivedApplications(userId, companyId, status, page, size);
    }
}
