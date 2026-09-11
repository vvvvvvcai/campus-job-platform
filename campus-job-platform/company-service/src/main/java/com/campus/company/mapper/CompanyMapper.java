package com.campus.company.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.company.entity.Company;
import org.apache.ibatis.annotations.Mapper;

/**
 * 企业Mapper
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
}
