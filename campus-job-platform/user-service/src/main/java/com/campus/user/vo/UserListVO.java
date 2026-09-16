package com.campus.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户列表响应VO（含分页）
 */
@Data
@ApiModel("用户列表响应")
public class UserListVO {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("当前页码")
    private Integer page;

    @ApiModelProperty("每页大小")
    private Integer pageSize;

    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("用户列表")
    private List<UserItemVO> records;

    /**
     * 用户列表项
     */
    @Data
    @ApiModel("用户列表项")
    public static class UserItemVO {

        @ApiModelProperty("用户ID")
        private Long id;

        @ApiModelProperty("手机号（脱敏）")
        private String phone;

        @ApiModelProperty("用户名")
        private String username;

        @ApiModelProperty("角色：0-学生，1-企业HR，2-管理员")
        private Integer role;

        @ApiModelProperty("角色标签")
        private String roleLabel;

        @ApiModelProperty("头像URL")
        private String avatar;

        @ApiModelProperty("邮箱")
        private String email;

        @ApiModelProperty("状态：0-禁用，1-正常")
        private Integer status;

        @ApiModelProperty("状态标签")
        private String statusLabel;

        @ApiModelProperty("创建时间")
        private LocalDateTime createTime;
    }
}
