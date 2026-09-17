package com.campus.recommend.controller;

import com.campus.common.result.Result;
import com.campus.recommend.config.ZhipuAiProperties;
import com.campus.recommend.dto.RecommendRefreshDTO;
import com.campus.recommend.service.RecommendService;
import com.campus.recommend.vo.RecommendJobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐控制器
 */
@Api(tags = "推荐管理")
@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;
    private final ZhipuAiProperties aiProperties;

    @ApiOperation("获取推荐模式状态")
    @GetMapping("/status")
    public Result<Map<String, Object>> getRecommendStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("aiEnabled", aiProperties.isEnabled());
        status.put("model", aiProperties.getModel());
        status.put("mode", aiProperties.isEnabled() ? "AI智能推荐" : "本地规则推荐");
        return Result.success(status);
    }

    @ApiOperation("获取推荐职位列表")
    @GetMapping("/jobs")
    public Result<List<RecommendJobVO>> getRecommendJobs(@RequestHeader("X-User-Id") Long userId) {
        return recommendService.getRecommendJobs(userId);
    }

    @ApiOperation("刷新推荐")
    @PostMapping("/refresh")
    public Result<List<RecommendJobVO>> refreshRecommend(@RequestHeader("X-User-Id") Long userId,
                                                        @RequestBody(required = false) RecommendRefreshDTO dto) {
        if (dto == null) {
            dto = new RecommendRefreshDTO();
        }
        dto.setUserId(userId);
        return recommendService.refreshRecommend(dto);
    }

    @ApiOperation("记录点击行为")
    @PostMapping("/click")
    public Result<Void> recordClick(@RequestHeader("X-User-Id") Long userId,
                                    @RequestParam Long jobId) {
        return recommendService.recordClick(userId, jobId);
    }
}
