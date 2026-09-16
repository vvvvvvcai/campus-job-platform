package com.campus.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 管理员职位列表响应VO（含分页）
 */
@Data
@ApiModel("管理员职位列表响应")
public class JobAdminListVO {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("当前页码")
    private Integer page;

    @ApiModelProperty("每页大小")
    private Integer pageSize;

    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("职位列表")
    private List<JobAdminItemVO> records;

    /**
     * 管理员职位列表项
     */
    @Data
    @ApiModel("管理员职位列表项")
    public static class JobAdminItemVO {

        @ApiModelProperty("职位ID")
        private Long id;

        @ApiModelProperty("职位标题")
        private String title;

        @ApiModelProperty("企业名称")
        private String companyName;

        @ApiModelProperty("职位类别")
        private String category;

        @ApiModelProperty("工作城市")
        private String city;

        @ApiModelProperty("最低薪资")
        private BigDecimal salaryMin;

        @ApiModelProperty("最高薪资")
        private BigDecimal salaryMax;

        @ApiModelProperty("学历要求")
        private String education;

        @ApiModelProperty("工作类型：1-全职，2-实习，3-兼职")
        private Integer jobType;

        @ApiModelProperty("招聘人数")
        private Integer headcount;

        @ApiModelProperty("职位状态：0-待审核，1-招聘中，2-已关闭")
        private Integer status;

        @ApiModelProperty("审核状态：0-待审核，1-已通过，2-已拒绝")
        private Integer auditStatus;

        @ApiModelProperty("审核备注")
        private String auditRemark;

        @ApiModelProperty("浏览次数")
        private Integer viewCount;

        @ApiModelProperty("申请次数")
        private Integer applyCount;

        @ApiModelProperty("创建时间")
        private LocalDateTime createTime;
    }
}
