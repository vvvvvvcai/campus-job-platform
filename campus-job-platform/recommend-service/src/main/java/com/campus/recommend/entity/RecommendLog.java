package com.campus.recommend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 推荐日志实体
 */
@Data
@TableName("t_recommend_log")
public class RecommendLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 推荐分数
     */
    private BigDecimal recommendScore;

    /**
     * 推荐理由
     */
    private String recommendReason;

    /**
     * 是否点击：0-未点击，1-已点击
     */
    private Integer isClicked;

    /**
     * 是否申请：0-未申请，1-已申请
     */
    private Integer isApplied;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
