package com.campus.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 职位搜索请求DTO
 */
@Data
@ApiModel("职位搜索请求")
public class JobSearchDTO {

    @ApiModelProperty("搜索关键词")
    private String keyword;

    @ApiModelProperty("工作城市")
    private String city;

    @ApiModelProperty("所属行业")
    private String industry;

    @ApiModelProperty("职位类别")
    private String category;

    @ApiModelProperty("工作类型：1-全职，2-实习，3-兼职")
    private Integer jobType;

    @ApiModelProperty("最低薪资")
    private BigDecimal salaryMin;

    @ApiModelProperty("最高薪资")
    private BigDecimal salaryMax;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty("排序方式：latest-最新发布，salary-薪资最高，response-投递响应最快，不传则综合排序")
    private String sortBy;
}
