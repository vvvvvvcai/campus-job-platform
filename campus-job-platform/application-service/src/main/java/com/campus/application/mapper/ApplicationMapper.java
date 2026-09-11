package com.campus.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.application.entity.Application;
import org.apache.ibatis.annotations.Mapper;

/**
 * 投递Mapper
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
}
