package com.campus.job.controller;

import com.campus.common.result.Result;
import com.campus.job.service.FavoriteService;
import com.campus.job.vo.JobInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏控制器
 */
@Api(tags = "收藏管理")
@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @ApiOperation("添加收藏")
    @PostMapping
    public Result<Void> addFavorite(@RequestHeader("X-User-Id") Long userId,
                                    @RequestParam Long jobId) {
        return favoriteService.addFavorite(userId, jobId);
    }

    @ApiOperation("取消收藏")
    @DeleteMapping("/{jobId}")
    public Result<Void> removeFavorite(@RequestHeader("X-User-Id") Long userId,
                                       @PathVariable Long jobId) {
        return favoriteService.removeFavorite(userId, jobId);
    }

    @ApiOperation("获取收藏列表")
    @GetMapping("/list")
    public Result<List<JobInfoVO>> getFavoriteList(@RequestHeader("X-User-Id") Long userId) {
        return favoriteService.getFavoriteList(userId);
    }
}
