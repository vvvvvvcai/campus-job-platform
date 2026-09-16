package com.campus.user.controller;

import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传控制器（图片存储在本机 uploads 目录，经网关 /uploads/** 静态访问）
 */
@Slf4j
@Api(tags = "文件上传")
@RestController
@RequestMapping("/api/upload")
public class FileController {

    private static final List<String> ALLOWED_EXT = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    @ApiOperation("上传图片（营业执照/身份证/简历附件等）")
    @PostMapping("/image")
    public Result<String> uploadImage(@RequestHeader("X-User-Id") Long userId,
                                      @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "请选择要上传的图片");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "图片大小不能超过5MB");
        }
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (ext == null || !ALLOWED_EXT.contains(ext.toLowerCase())) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "仅支持 jpg/png/gif/webp 格式图片");
        }

        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = userId + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12) + "." + ext.toLowerCase();
        File dir = new File("uploads", dateDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new BusinessException(ResultCode.FAIL.getCode(), "创建存储目录失败");
        }
        try {
            file.transferTo(new File(dir, filename).getAbsoluteFile());
        } catch (IOException e) {
            log.error("图片保存失败: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.FAIL.getCode(), "图片保存失败，请重试");
        }

        String url = "/uploads/" + dateDir + "/" + filename;
        log.info("图片上传成功: userId={}, url={}, size={}", userId, url, file.getSize());
        return Result.success(url);
    }
}
