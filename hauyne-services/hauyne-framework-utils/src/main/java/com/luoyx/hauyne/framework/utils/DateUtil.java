package com.luoyx.hauyne.framework.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author 1564469545@qq.com
 */
@UtilityClass
public class DateUtil {

    public static final String DATETIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_MINITU_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMATTER = "yyyy-MM-dd";

    public static final String TIME_FORMATTER = "HH:mm:ss";

    public static final String DATETIME_T_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 时间带毫秒的表达式
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * UTC 时间表达式，带毫秒
     */
    public static final String UTC_DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * 带时区的时间表达式
     */
    public static final String ZONE_DATA_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";



    /**
     * 返回当前时间 (HH:mm:ss.SSS)
     */
    public static LocalTime getTime() {
        return LocalTime.now();
    }

    /**
     * 获取【当前日期】字符串 (yyyy-MM-dd格式)
     */
    public static String getDateStr() {
        return DateTimeFormatter.ofPattern(DATE_FORMATTER).format(LocalDateTime.now());
    }

    /**
     * 返回【当前时间】字符串
     */
    public static String getTimeStr() {
        return DateTimeFormatter.ofPattern(TIME_FORMATTER).format(LocalDateTime.now());
    }

    /**
     * 获取【当前日期时间】字符串(yyyy-MM-dd HH:mm:ss)
     */
    public static String getDateTimeStr() {
        return DateTimeFormatter.ofPattern(DATETIME_FORMATTER).format(LocalDateTime.now());
    }

    /**
     * 获取【当前日期时间，带毫秒】字符串（yyyy-MM-dd HH:mm:ss.SSS）
     */
    public static String getMillDateTimeStr() {
        return DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS_SSS).format(LocalDateTime.now());
    }

    /**
     * 将long类型的timestamp转为LocalDateTime
     *
     * @param timestamp 时间戳
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * 将LocalDateTime转为long类型的timestamp
     *
     * @param time 日期
     * @return 返回时间戳
     */
    public static long toLong(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将date转为当前系统默认时区的LocalDateTime
     *
     * @param time 需要转化的时间
     * @return 返回当前系统默认时区的LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date time) {
        return time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 将时间字符串转为LocalDateTime
     *
     * @param time 需要转化的时间字符串
     * @return 返回LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String time) {
        return toLocalDateTime(time, DATETIME_FORMATTER);
    }

    /**
     * 将时间字符串转为自定义时间格式的LocalDateTime
     *
     * @param time   需要转化的时间字符串
     * @param format 自定义的时间格式
     * @return 返回日期
     */
    public static LocalDateTime toLocalDateTime(String time, String format) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 将LocalDateTime转为自定义的时间格式的字符串
     *
     * @param time 需要转化的LocalDateTime
     * @return 返回格式化的日期字符串（yyyy-MM-dd HH:mm:ss）
     * @throws
     * @description:
     */
    public static String toString(LocalDateTime time) {
        return toString(time, DATETIME_FORMATTER);
    }

    /**
     * 将LocalDateTime转为自定义时间格式的字符串
     *
     * @param time   需要转化的LocalDateTime
     * @param format 自定义的时间格式
     * @return 返回指定格式的日期字符串
     */
    public static String toString(LocalDateTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 时区转换
     *
     * @param time       日期
     * @param sourceZone 源时区
     * @param targetZone 目标时区
     * @return 返回转换时区后的日期
     */
    public static LocalDateTime toZone(LocalDateTime time, ZoneId sourceZone, ZoneId targetZone) {
        final ZonedDateTime zonedtime = time.atZone(sourceZone);
        final ZonedDateTime convertedTargetZoneDateTime = zonedtime.withZoneSameInstant(targetZone);

        return convertedTargetZoneDateTime.toLocalDateTime();
    }

    /**
     * 根据系统默认时区转换到指定时区
     *
     * @param time       日期
     * @param targetZone 目标时区
     * @return 返回转换时区后的日期
     */
    public static LocalDateTime toZone(LocalDateTime time, ZoneId targetZone) {
        return toZone(time, ZoneId.systemDefault(), targetZone);
    }

    /**
     * 将【源时区的时间】转为【UTC时间】
     *
     * @param time       日期
     * @param sourceZone 源时区
     * @return 返回转换时区后的日期
     */
    public static LocalDateTime toUtc(LocalDateTime time, ZoneId sourceZone) {
        return toZone(time, sourceZone, ZoneOffset.UTC);
    }

    /**
     * 将【系统默认时区的时间】转换为UTC时间
     *
     * @param time 日期
     * @return 返回UTC时间
     */
    public static LocalDateTime toUtc(LocalDateTime time) {
        return toUtc(time, ZoneId.systemDefault());
    }

    /**
     * 获取当前时间的UTC时间戳
     *
     * @return 返回当前时间的UTC时间戳，精确到毫秒
     */
    public static long getCurrentUtcTimestamp() {
        return toLong(toUtc(LocalDateTime.now()));
    }

    /**
     * 返回当前时间，带有时区格式的，
     * <p>
     * 示例：2022-07-14T11:21:00.978+08:00 表示北京时间，东8区
     *
     * @return 返回当前时间，带有时区格式的
     */
    public static String getZoneDateTimeStr() {
        return new SimpleDateFormat(ZONE_DATA_FORMATTER).format(new Date());
    }

    /**
     * UTC时间字符串转成北京时间戳
     *
     * @param utcStr 字符串格式，"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * @return 北京时间戳
     */
    public static Long utcTimeStrToBeijingTimestamp(String utcStr) {
        LocalDateTime utcDateTime = toLocalDateTime(utcStr, UTC_DATE_FORMATTER);
        LocalDateTime beijingDateTime = toZone(utcDateTime, ZoneOffset.UTC, ZoneId.of("Asia/Shanghai"));

        return toLong(beijingDateTime);
    }
}

