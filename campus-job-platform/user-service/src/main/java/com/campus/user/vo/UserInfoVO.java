package com.campus.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息响应VO
 */
@Data
@ApiModel("用户信息响应")
public class UserInfoVO {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("角色：0-学生，1-企业HR，2-管理员")
    private Integer role;

    @ApiModelProperty("头像URL")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("性别：0-未知，1-男，2-女")
    private Integer gender;

    @ApiModelProperty("状态：0-禁用，1-正常")
    private Integer status;
}
