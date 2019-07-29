package com.jianghu.mscore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * string工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.21
 */
public class StringUtil extends StringUtils {
    /**
     * The constant PATTERN_A_Z.
     */
    public static final Pattern PATTERN_A_Z = Pattern.compile("[A-Z]");
    /**
     * The constant PATTERN__A_Z.
     */
    public static final Pattern PATTERN__A_Z = Pattern.compile("_[a-z]");


    /**
     * 正则匹配
     *
     * @param originStr the origin str
     * @param matchStr  the match str
     * @return the boolean
     * @since 2019.06.21
     */
    public static boolean regexMatch(String originStr, String matchStr) {
        if (isEmpty(originStr) && isNotEmpty(matchStr)) {
            return false;
        } else {
            return !isEmpty(matchStr) && originStr.matches(matchStr);
        }
    }

    /**
     * 替换字符串
     *
     * @param str         the str
     * @param regex       the regex
     * @param replacement the replacement
     * @return the string
     * @since 2019.06.21
     */
    public static String replaceAllWithRegex(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            m.appendReplacement(sb, replacement);
        }

        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰变下划线
     *
     * @param str the str
     * @return the string
     * @since 2019.06.21
     */
    public static String camelhumpToUnderline(String str) {
        Matcher matcher = PATTERN_A_Z.matcher(str);
        StringBuilder builder = new StringBuilder(str);

        for(int i = 0; matcher.find(); ++i) {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
        }

        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }

        return builder.toString();
    }

    /**
     * 下划线变驼峰
     *
     * @param str the str
     * @return the string
     * @since 2019.06.21
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = PATTERN__A_Z.matcher(str);
        StringBuilder builder = new StringBuilder(str);

        for(int i = 0; matcher.find(); ++i) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }

        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }

        return builder.toString();
    }

    /**
     * 首字母大写
     *
     * @param str the str
     * @return the string
     * @since 2019.06.21
     */
    public static String firstToUpper(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str the str
     * @return the string
     * @since 2019.06.21
     */
    public static String firstToLower(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}

