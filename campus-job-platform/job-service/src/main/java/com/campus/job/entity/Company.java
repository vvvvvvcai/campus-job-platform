package com.campus.job.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_company")
public class Company {
    private Long id;
    private Long userId;
    private String companyName;
    @TableLogic
    private Integer deleted;
}
