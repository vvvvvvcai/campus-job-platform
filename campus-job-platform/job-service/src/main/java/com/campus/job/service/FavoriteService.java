package com.campus.job.service;

import com.campus.common.result.Result;
import com.campus.job.vo.JobInfoVO;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface FavoriteService {

    /**
     * 添加收藏
     */
    Result<Void> addFavorite(Long userId, Long jobId);

    /**
     * 取消收藏
     */
    Result<Void> removeFavorite(Long userId, Long jobId);

    /**
     * 获取收藏列表
     */
    Result<List<JobInfoVO>> getFavoriteList(Long userId);
}
