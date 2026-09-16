package com.campus.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.campus.application.entity.Application;
import com.campus.application.vo.ApplicationInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 投递Mapper
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    /**
     * 分页查询投递列表（联表带出职位名/企业名/简历标题）
     * userId 与 companyId 二选一：userId 用于学生端「我的投递」，companyId 用于企业端「收到的投递」
     */
    IPage<ApplicationInfoVO> selectApplicationList(IPage<ApplicationInfoVO> page,
                                                   @Param("userId") Long userId,
                                                   @Param("companyId") Long companyId,
                                                   @Param("status") Integer status);
}
