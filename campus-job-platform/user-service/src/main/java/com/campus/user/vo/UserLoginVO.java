package com.campus.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录响应VO
 */
@Data
@ApiModel("用户登录响应")
public class UserLoginVO {

    @ApiModelProperty("Token")
    private String token;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("角色：0-学生，1-企业HR，2-管理员")
    private Integer role;

    @ApiModelProperty("头像URL")
    private String avatar;
}
