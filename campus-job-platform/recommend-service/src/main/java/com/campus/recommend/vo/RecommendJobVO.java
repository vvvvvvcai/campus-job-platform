package com.campus.recommend.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 推荐职位响应VO
 */
@Data
@ApiModel("推荐职位信息")
public class RecommendJobVO {

    @ApiModelProperty("职位ID")
    private Long jobId;

    @ApiModelProperty("职位标题")
    private String title;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("工作城市")
    private String city;

    @ApiModelProperty("最低薪资")
    private BigDecimal salaryMin;

    @ApiModelProperty("最高薪资")
    private BigDecimal salaryMax;

    @ApiModelProperty("推荐分数")
    private BigDecimal recommendScore;

    @ApiModelProperty("推荐理由")
    private String recommendReason;
}
