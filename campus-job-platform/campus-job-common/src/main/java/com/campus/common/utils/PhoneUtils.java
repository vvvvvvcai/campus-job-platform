package com.campus.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * 手机号工具类
 */
public class PhoneUtils {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 验证手机号格式
     */
    public static boolean isValid(String phone) {
        if (StrUtil.isBlank(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 手机号脱敏
     * 例如：138****8888
     */
    public static String desensitize(String phone) {
        if (StrUtil.isBlank(phone) || phone.length() != 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }
}
