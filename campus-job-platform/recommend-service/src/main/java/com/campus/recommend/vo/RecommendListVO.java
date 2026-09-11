package com.campus.recommend.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 推荐列表响应VO
 */
@Data
@ApiModel("推荐列表响应")
public class RecommendListVO {

    @ApiModelProperty("推荐职位列表")
    private List<RecommendJobVO> jobs;

    @ApiModelProperty("推荐总数")
    private Integer total;
}
