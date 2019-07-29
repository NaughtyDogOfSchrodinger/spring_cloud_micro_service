package com.jianghu.mscore.web.constant;

import java.text.SimpleDateFormat;

/**
 * 时间格式化
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.29
 */
public interface DateFormat {
    /**
     * The constant DEFAULT_SHORT_FORMAT.
     */
    SimpleDateFormat DEFAULT_SHORT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * The constant DEFAULT_LONG_FORMAT.
     */
    SimpleDateFormat DEFAULT_LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * The constant DEFAULT_MINUTE_FORMAT.
     */
    SimpleDateFormat DEFAULT_MINUTE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    SimpleDateFormat DEFAULT_NO_CHAR_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");


}
