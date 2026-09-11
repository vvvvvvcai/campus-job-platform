package com.campus.recommend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户画像实体
 */
@Data
@TableName("t_user_profile")
public class UserProfile {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 意向城市
     */
    private String intentCity;

    /**
     * 意向行业
     */
    private String intentIndustry;

    /**
     * 意向职位类型
     */
    private String intentJobType;

    /**
     * 浏览次数
     */
    private Integer browseCount;

    /**
     * 申请次数
     */
    private Integer applyCount;

    /**
     * 收藏次数
     */
    private Integer favoriteCount;

    /**
     * 最后浏览时间
     */
    private LocalDateTime lastBrowseTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
