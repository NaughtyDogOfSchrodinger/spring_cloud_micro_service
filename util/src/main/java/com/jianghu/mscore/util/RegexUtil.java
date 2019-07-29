package com.jianghu.mscore.util;

/**
 * 正则工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.21
 */
public class RegexUtil {
    /**
     * The constant ONLY_ALPHA_RGX.
     */
    public static final String ONLY_ALPHA_RGX = "^[a-zA-Z]+$";
    /**
     * The constant ALPHA_OR_NUM_RGX.
     */
    public static final String ALPHA_OR_NUM_RGX = "^[a-zA-Z0-9]+$";
    /**
     * The constant ONLY_INTEGER_RGX.
     */
    public static final String ONLY_INTEGER_RGX = "^[\\d]+$";
    /**
     * The constant ONLY_VALID_INTEGER_RGX.
     */
    public static final String ONLY_VALID_INTEGER_RGX = "^[-+]?[1-9]\\d*$|^0$/";
    /**
     * The constant ONLY_CHINESE_RGX.
     */
    public static final String ONLY_CHINESE_RGX = "^[一-龥]+$";
    /**
     * The constant EMAIL_RGX.
     */
    public static final String EMAIL_RGX = "\\w{1,}[@][\\w\\-]{1,}([.]([\\w\\-]{1,})){1,3}$";
    /**
     * The constant IP_RGX.
     */
    public static final String IP_RGX = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    /**
     * The constant PHONE_NUM_RGX.
     */
    public static final String PHONE_NUM_RGX = "(^0?[1][3578][0-9]{9}$)";
    /**
     * The constant FIXED_PHONE_NUM_RGX.
     */
    public static final String FIXED_PHONE_NUM_RGX = "^((0[1-9]{3})?(0[12][0-9])?[-])?\\d{6,8}$";
    /**
     * The constant POST_CODE_RGX.
     */
    public static final String POST_CODE_RGX = "^[1-9]\\d{5}$";

}

