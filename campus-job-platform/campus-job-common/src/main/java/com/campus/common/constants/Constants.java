package com.campus.common.constants;

/**
 * 系统常量
 */
public class Constants {

    /**
     * Token header前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Token header名称
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 用户ID属性名
     */
    public static final String USER_ID = "userId";

    /**
     * 手机号属性名
     */
    public static final String PHONE = "phone";

    /**
     * 角色属性名
     */
    public static final String ROLE = "role";

    /**
     * 用户角色：学生
     */
    public static final Integer ROLE_STUDENT = 0;

    /**
     * 用户角色：企业HR
     */
    public static final Integer ROLE_COMPANY = 1;

    /**
     * 用户角色：管理员
     */
    public static final Integer ROLE_ADMIN = 2;

    /**
     * 分页默认页码
     */
    public static final Integer DEFAULT_PAGE = 1;

    /**
     * 分页默认每页条数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 分页最大每页条数
     */
    public static final Integer MAX_PAGE_SIZE = 100;
}
