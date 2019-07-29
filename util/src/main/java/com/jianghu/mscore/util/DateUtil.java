package com.jianghu.mscore.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.20
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * The constant DEFAULT_SHORT_FORMAT.
     */
    public static final String DEFAULT_SHORT_FORMAT = "yyyy-MM-dd";
    /**
     * The constant DEFAULT_LONG_FORMAT.
     */
    public static final String DEFAULT_LONG_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant DEFAULT_MINUTE_FORMAT.
     */
    public static final String DEFAULT_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";


    /**
     * 字符串转年月日
     *
     * @param str the str
     * @return the date
     * @since 2019.06.20
     */
    public static Date string2DateDay(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        str = StringUtil.trimToNull(str);

        try {
            return formatter.parse(str);
        } catch (ParseException var4) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
    }


    /**
     * 字符串转年月日时分秒
     *
     * @param str the str
     * @return the date
     * @since 2019.06.20
     */
    public static Date string2DateSecond24(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str = StringUtil.trimToNull(str);
        String matchStr = "[0-2]\\d\\d\\d-\\d\\d-\\d\\d [0-2]\\d:[0-6]\\d";
        if (StringUtil.regexMatch(str, matchStr)) {
            str = str + ":00";
        }

        try {
            return formatter.parse(str);
        } catch (ParseException var4) {
            return new Date();
        }
    }

    /**
     * Stirng 2 date minute date.
     *
     * @param str the str
     * @return the date
     * @since 2019.06.20
     */
    public static Date stirng2DateMinute(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        str = StringUtil.trimToNull(str);

        try {
            return formatter.parse(str);
        } catch (ParseException var3) {
            return new Date();
        }
    }

    /**
     * Stirng 2 date date.
     *
     * @param str          the str
     * @param formatString the format string
     * @return the date
     * @since 2019.06.20
     */
    public static Date stirng2Date(String str, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        str = StringUtil.trimToNull(str);

        try {
            return formatter.parse(str);
        } catch (ParseException var4) {
            return new Date();
        } catch (IllegalArgumentException var5) {
            logger.warn("format string Illegal: {},{}", formatString, var5);
            return null;
        }
    }

    /**
     * String 2 date 4 null date.
     *
     * @param str          the str
     * @param formatString the format string
     * @return the date
     * @since 2019.06.20
     */
    public static Date string2Date4Null(String str, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        str = StringUtil.trimToNull(str);

        try {
            return formatter.parse(str);
        } catch (Exception var4) {
            return null;
        }
    }

    /**
     * Date 2 string string.
     *
     * @param date the date
     * @return the string
     * @since 2019.06.20
     */
    public static String date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * Date 2 int day integer.
     *
     * @param date the date
     * @return the integer
     * @since 2019.06.20
     */
    public static Integer date2IntDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * Seconds to string string.
     *
     * @param seconds the seconds
     * @return the string
     * @since 2019.06.20
     */
    public static String secondsToString(Integer seconds) {
        return date2String(fromUnixTime(seconds));
    }

    /**
     * Date 2 string min string.
     *
     * @param date the date
     * @return the string
     * @since 2019.06.20
     */
    public static String date2StringMin(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }

    /**
     * Date 2 string sec string.
     *
     * @param date the date
     * @return the string
     * @since 2019.06.20
     */
    public static String date2StringSec(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * Date 2 string sec string.
     *
     * @param seconds the seconds
     * @return the string
     * @since 2019.06.20
     */
    public static String date2StringSec(Integer seconds) {
        return date2StringSec(fromUnixTime(seconds));
    }

    /**
     * Date 2 string string.
     *
     * @param date         the date
     * @param formatString the format string
     * @return the string
     * @since 2019.06.20
     */
    public static String date2String(Date date, String formatString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatString);
            return formatter.format(date);
        } catch (IllegalArgumentException var3) {
            logger.warn("format string Illegal: {},{}", formatString, var3);
            return "";
        }
    }

    /**
     * Unix time int.
     *
     * @return the int
     * @since 2019.06.20
     */
    public static int unixTime() {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    /**
     * From unix time date.
     *
     * @param seconds the seconds
     * @return the date
     * @since 2019.06.20
     */
    public static Date fromUnixTime(Integer seconds) {
        return new Date((long)seconds * 1000L);
    }

    /**
     * Today date.
     *
     * @return the date
     * @since 2019.06.20
     */
    public static Date today() {
        return toDay(new Date());
    }

    /**
     * To day date.
     *
     * @param date the date
     * @return the date
     * @since 2019.06.20
     */
    public static Date toDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * To night date.
     *
     * @param date the date
     * @return the date
     * @since 2019.06.20
     */
    public static Date toNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Date between list.
     *
     * @param startDateStr the start date str
     * @param endDateStr   the end date str
     * @return the list
     * @since 2019.06.20
     */
    public static List<String> dateBetween(String startDateStr, String endDateStr) {
        List<String> dateList = new ArrayList<>();
        Date endDate = string2DateDay(endDateStr);
        Date startDate = string2DateDay(startDateStr);
        long day = (endDate.getTime() - startDate.getTime()) / 86400000L;
        Calendar cal = Calendar.getInstance();

        for(int i = 0; (long)i <= day; ++i) {
            cal.setTime(startDate);
            cal.add(5, i);
            dateList.add(date2String(cal.getTime()));
        }

        return dateList;
    }

    /**
     * To yesterday date.
     *
     * @param date the date
     * @return the date
     * @since 2019.06.20
     */
    public static Date toYesterday(Date date) {
        return add(date, 6, -1);
    }

    /**
     * To tommorow date.
     *
     * @param date the date
     * @return the date
     * @since 2019.06.20
     */
    public static Date toTommorow(Date date) {
        return add(date, 6, 1);
    }

    private static Date add(Date date, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(field, value);
        return cal.getTime();
    }

    /**
     * Gets month days.
     *
     * @param year  the year
     * @param month the month
     * @return the month days
     */
    public static int getMonthDays(Integer year, Integer month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1);
        return c.getActualMaximum(5);
    }

    /**
     * Gets year.
     *
     * @param date the date
     * @return the year
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(1);
    }

    /**
     * Gets month.
     *
     * @param date the date
     * @return the month
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(2) + 1;
    }

    /**
     * Gets day of week.
     *
     * @param date the date
     * @return the day of week
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int dayOfWeek = cal.get(7);
        byte rel;
        switch(dayOfWeek) {
            case 2:
                rel = 1;
                break;
            case 3:
                rel = 2;
                break;
            case 4:
                rel = 3;
                break;
            case 5:
                rel = 4;
                break;
            case 6:
                rel = 5;
                break;
            case 7:
                rel = 6;
                break;
            default:
                rel = 7;
        }

        return rel;
    }

    /**
     * Day 2 unixtime int.
     *
     * @param day the day
     * @return the int
     * @since 2019.06.20
     */
    public static int day2Unixtime(String day) {
        return (int)(string2DateDay(day).getTime() / 1000L);
    }

    /**
     * Date 2 unixtime int.
     *
     * @param date the date
     * @return the int
     * @since 2019.06.20
     */
    public static int date2Unixtime(Date date) {
        return (int)(date.getTime() / 1000L);
    }

    /**
     * To time date.
     *
     * @param date the date
     * @return the date
     * @since 2019.06.20
     */
    public static Date toTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * Is exceed some days boolean.
     *
     * @param date     the date
     * @param dayCount the day count
     * @return the boolean
     * @since 2019.06.20
     */
    public static Boolean isExceedSomeDays(Date date, Integer dayCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dayCount);
        return calendar.getTime().before(new Date());
    }

    /**
     * Is same date boolean.
     *
     * @param firtDate   the firt date
     * @param secondDate the second date
     * @return the boolean
     * @since 2019.06.20
     */
    public static boolean isSameDate(Date firtDate, Date secondDate) {
        return toDay(firtDate).equals(toDay(secondDate));
    }

    /**
     * String to timestamp timestamp.
     *
     * @param time the time
     * @return the timestamp
     * @since 2019.06.20
     */
    public static Timestamp stringToTimestamp(String time) {
        return new Timestamp(Objects.requireNonNull(stirng2Date(time, "yyyy-MM-dd HH:mm:ss")).getTime());
    }

    /**
     * Int second 2 str string.
     *
     * @param second the second
     * @return the string
     * @since 2019.06.20
     */
    public static String intSecond2Str(Integer second) {
        long time = (long)second * 1000L;
        Date date = new Date(time);
        return date2StringSec(date);
    }

    /**
     * Trans date str string.
     *
     * @param dateStr the date str
     * @return the string
     * @since 2019.06.20
     */
    public static String transDateStr(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        try {
            Date date = formatter.parse(dateStr);
            return date2String(date, "yyyy-MM-dd");
        } catch (ParseException var3) {
            return null;
        }
    }

    /**
     * Gets date before.
     *
     * @param d   the d
     * @param day the day
     * @return the date before
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }
}

