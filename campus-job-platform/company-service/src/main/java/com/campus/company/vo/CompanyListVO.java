package com.campus.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 企业列表响应VO（含分页）
 */
@Data
@ApiModel("企业列表响应")
public class CompanyListVO {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("当前页码")
    private Integer page;

    @ApiModelProperty("每页大小")
    private Integer pageSize;

    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("企业列表")
    private List<CompanyItemVO> records;

    /**
     * 企业列表项
     */
    @Data
    @ApiModel("企业列表项")
    public static class CompanyItemVO {

        @ApiModelProperty("企业ID")
        private Long id;

        @ApiModelProperty("用户ID")
        private Long userId;

        @ApiModelProperty("企业名称")
        private String companyName;

        @ApiModelProperty("所属行业")
        private String industry;

        @ApiModelProperty("企业规模")
        private String scale;

        @ApiModelProperty("营业执照URL")
        private String licenseUrl;

        @ApiModelProperty("审核状态：0-待审核，1-已通过，2-已拒绝")
        private Integer auditStatus;

        @ApiModelProperty("审核状态标签")
        private String auditStatusLabel;

        @ApiModelProperty("审核备注")
        private String auditRemark;

        @ApiModelProperty("创建时间")
        private LocalDateTime createTime;
    }
}
