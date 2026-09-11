package com.campus.recommend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 推荐刷新请求DTO
 */
@Data
@ApiModel("推荐刷新请求")
public class RecommendRefreshDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("城市筛选")
    private String city;

    @ApiModelProperty("行业筛选")
    private String industry;

    @ApiModelProperty("职位类型筛选")
    private String jobType;
}
