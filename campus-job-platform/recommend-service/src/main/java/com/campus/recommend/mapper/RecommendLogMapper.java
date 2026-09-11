package com.campus.recommend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.recommend.entity.RecommendLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 推荐日志Mapper
 */
@Mapper
public interface RecommendLogMapper extends BaseMapper<RecommendLog> {
}
