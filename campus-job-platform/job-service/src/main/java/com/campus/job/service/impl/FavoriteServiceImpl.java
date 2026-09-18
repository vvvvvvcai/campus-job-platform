package com.campus.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import com.campus.job.entity.Company;
import com.campus.job.entity.Favorite;
import com.campus.job.entity.Job;
import com.campus.job.mapper.CompanyMapper;
import com.campus.job.mapper.FavoriteMapper;
import com.campus.job.mapper.JobMapper;
import com.campus.job.service.FavoriteService;
import com.campus.job.vo.JobInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final JobMapper jobMapper;
    private final CompanyMapper companyMapper;

    @Override
    public Result<Void> addFavorite(Long userId, Long jobId) {
        // 检查职位是否存在
        Job job = jobMapper.selectById(jobId);
        if (job == null) {
            throw new BusinessException(ResultCode.JOB_NOT_FOUND);
        }

        // 检查是否已收藏
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        wrapper.eq(Favorite::getJobId, jobId);
        if (favoriteMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.FAIL.getCode(), "已收藏该职位");
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setJobId(jobId);
        favoriteMapper.insert(favorite);

        log.info("用户{}收藏职位{}", userId, jobId);
        return Result.success();
    }

    @Override
    public Result<Void> removeFavorite(Long userId, Long jobId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        wrapper.eq(Favorite::getJobId, jobId);

        int deleted = favoriteMapper.delete(wrapper);
        if (deleted == 0) {
            throw new BusinessException(ResultCode.FAIL.getCode(), "未收藏该职位");
        }

        log.info("用户{}取消收藏职位{}", userId, jobId);
        return Result.success();
    }

    @Override
    public Result<List<JobInfoVO>> getFavoriteList(Long userId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        wrapper.orderByDesc(Favorite::getCreateTime);

        List<Favorite> favorites = favoriteMapper.selectList(wrapper);

        List<JobInfoVO> result = favorites.stream().map(favorite -> {
            Job job = jobMapper.selectById(favorite.getJobId());
            if (job != null) {
                JobInfoVO vo = new JobInfoVO();
                BeanUtils.copyProperties(job, vo);
                vo.setIsFavorite(true);
                Company company = companyMapper.selectById(job.getCompanyId());
                if (company != null) {
                    vo.setCompanyName(company.getCompanyName());
                    vo.setPublisherId(company.getUserId());
                }
                return vo;
            }
            return null;
        }).filter(vo -> vo != null).collect(Collectors.toList());

        return Result.success(result);
    }
}
