package com.campus.recommend.service;

import com.campus.common.result.Result;
import com.campus.recommend.dto.RecommendRefreshDTO;
import com.campus.recommend.vo.RecommendJobVO;

import java.util.List;

/**
 * 推荐服务接口
 */
public interface RecommendService {

    /**
     * 获取推荐职位列表
     */
    Result<List<RecommendJobVO>> getRecommendJobs(Long userId);

    /**
     * 刷新推荐
     */
    Result<List<RecommendJobVO>> refreshRecommend(RecommendRefreshDTO dto);

    /**
     * 记录点击行为
     */
    Result<Void> recordClick(Long userId, Long jobId);
}
