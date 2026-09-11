package com.campus.application.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 投递列表响应VO
 */
@Data
@ApiModel("投递列表响应")
public class ApplicationListVO {

    @ApiModelProperty("投递列表")
    private List<ApplicationInfoVO> records;

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("当前页码")
    private Long page;

    @ApiModelProperty("每页大小")
    private Long size;

    @ApiModelProperty("总页数")
    private Long pages;
}
