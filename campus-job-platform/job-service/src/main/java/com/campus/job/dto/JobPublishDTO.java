package com.campus.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 职位发布请求DTO
 */
@Data
@ApiModel("职位发布请求")
public class JobPublishDTO {

    @NotNull(message = "企业ID不能为空")
    @ApiModelProperty(value = "企业ID", required = true)
    private Long companyId;

    @NotBlank(message = "职位标题不能为空")
    @ApiModelProperty(value = "职位标题", required = true)
    private String title;

    @NotBlank(message = "职位类别不能为空")
    @ApiModelProperty(value = "职位类别", required = true)
    private String category;

    @NotBlank(message = "工作城市不能为空")
    @ApiModelProperty(value = "工作城市", required = true)
    private String city;

    @NotNull(message = "最低薪资不能为空")
    @ApiModelProperty(value = "最低薪资", required = true)
    private BigDecimal salaryMin;

    @NotNull(message = "最高薪资不能为空")
    @ApiModelProperty(value = "最高薪资", required = true)
    private BigDecimal salaryMax;

    @NotBlank(message = "学历要求不能为空")
    @ApiModelProperty(value = "学历要求", required = true)
    private String education;

    @NotBlank(message = "经验要求不能为空")
    @ApiModelProperty(value = "经验要求", required = true)
    private String experience;

    @NotNull(message = "工作类型不能为空")
    @ApiModelProperty(value = "工作类型：1-全职，2-实习，3-兼职", required = true)
    private Integer jobType;

    @ApiModelProperty("职位描述")
    private String description;

    @ApiModelProperty("任职要求")
    private String requirement;

    @ApiModelProperty("福利待遇")
    private String benefits;

    @ApiModelProperty("招聘人数")
    private Integer headcount;
}
