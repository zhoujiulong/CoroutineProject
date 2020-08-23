package com.zhoujiulong.baselib.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object DateUtil {
    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    val currentTime: Long
        get() {
            val calendar = Calendar.getInstance()
            return calendar.timeInMillis
        }

    /**
     * 获取当前天
     *
     * @return 天
     */
    val currentDay: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

    /**
     * 获取当前月
     *
     * @return 月
     */
    val currentMonth: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.MONTH)
        }

    /**
     * 获取当前时
     */
    val currentHour: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.HOUR_OF_DAY)
        }

    /**
     * 获取当前年
     *
     * @return 年
     */
    val currentYear: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.YEAR)
        }

    /**
     * 获取当前秒
     *
     * @return 秒
     */
    val currentSecond: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.SECOND)
        }

    /**
     * 格式化12小时制<br></br>
     * 格式：yyyy-MM-dd hh-mm-ss
     *
     * @param time 时间
     */
    fun format12Time(time: Long): String {
        return format(time, "yyyy-MM-dd hh:mm:ss")
    }

    /**
     * 格式化24小时制<br></br>
     * 格式：yyyy-MM-dd HH-mm-ss
     *
     * @param time 时间
     */
    fun format24Time(time: Long): String {
        return format(time, "yyyy-MM-dd HH:mm:ss")
    }

    /**
     * 格式化到天<br></br>
     * 格式：yyyy-MM-dd
     *
     * @param time 时间
     */
    fun formatDay(time: Long): String {
        return format(time, "yyyy-MM-dd")
    }

    /**
     * 格式化时间,自定义标签
     *
     * @param time    时间
     * @param pattern 格式化时间用的标签
     */
    fun format(time: Long, pattern: String): String {
        val sdf = SimpleDateFormat(pattern, Locale.CHINA)
        return sdf.format(Date(time))
    }

    /**
     * 根据时间格式返回时间戳
     * @format 如：yyyy-MM-dd hh:mm:ss
     */
    fun getCurrentTime(time: String, format: String): Long {
        val dateFormat = SimpleDateFormat(format, Locale.CHINA)
        var date: Long = 0
        try {
            date = dateFormat.parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 根据时间格式返回年月
     * @format 如：yyyy-MM-dd hh:mm:ss
     */
    fun getYearMonth(time: String, format: String): String {
        var timeString = ""
        val dateFormat = SimpleDateFormat(format, Locale.CHINA)
        val date: Long
        try {
            date = dateFormat.parse(time).time
            timeString = if (date <= 0) "" else format(date, "yyyy-MM")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timeString
    }

    /**
     * 判断2个时间是不是同一天
     */
    fun areSameDay(dateA: Date, dateB: Date): Boolean {
        val calDateA = Calendar.getInstance()
        calDateA.time = dateA

        val calDateB = Calendar.getInstance()
        calDateB.time = dateB

        return (calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH))
    }


    //毫秒换成00:00:00
    fun getCountTimeByLong(finishTime: Long, showHourIfZero: Boolean): String {
        return getCountTimeByLong(finishTime, ":", ":", "", showHourIfZero)
    }

    //毫秒换成00:00:00
    fun getCountTimeByLong(
        finishTime: Long,
        hourStr: String,
        minStr: String,
        secondStr: String,
        showHourIfZero: Boolean
    ): String {
        var totalTime = (finishTime / 1000).toInt()//秒
        var hour = 0
        var minute = 0
        var second = 0

        if (3600 <= totalTime) {
            hour = totalTime / 3600
            totalTime = totalTime - 3600 * hour
        }
        if (60 <= totalTime) {
            minute = totalTime / 60
            totalTime = totalTime - 60 * minute
        }
        if (0 <= totalTime) {
            second = totalTime
        }
        val sb = StringBuilder()

        if (hour != 0 || showHourIfZero) {
            if (hour < 10) {
                sb.append("0").append(hour).append(hourStr)
            } else {
                sb.append(hour).append(hourStr)
            }
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(minStr)
        } else {
            sb.append(minute).append(minStr)
        }
        if (second < 10) {
            sb.append("0").append(second).append(secondStr)
        } else {
            sb.append(second).append(secondStr)
        }
        return sb.toString()

    }

    /**
     * 毫秒转化时分秒毫秒
     */
    fun formatTime(ms: Long?): String {
        val ss = 1000
        val mi = ss * 60
        val hh = mi * 60
        val dd = hh * 24

        val day = ms!! / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = (ms - day * dd - hour * hh - minute * mi) / ss
        val milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss

        val sb = StringBuffer()
        if (day > 0) {
            sb.append(day).append("天")
        }
        if (hour > 0) {
            sb.append(hour).append("小时")
        }
        if (minute > 0) {
            sb.append(minute).append("分")
        }
        if (second > 0) {
            sb.append(second).append("秒")
        }
        if (milliSecond > 0) {
            sb.append(milliSecond).append("毫秒")
        }
        return sb.toString()
    }

    /**
     * 匹配字符串是否是时间格式 "20xx-xx-xx"
     */
    fun valiDateTimeWithLongFormat(timeStr: String): Boolean {
        val format = "((20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"
        val pattern = Pattern.compile(format)
        val matcher = pattern.matcher(timeStr)
        return matcher.matches()
    }

    /**
     * 秒转化时分秒
     */
    fun formatTimeS(ms: Long): String {
        val ss = 1
        val mi = ss * 60

        val minute = ms / mi
        val second = (ms - minute * mi) / ss


        val sb = StringBuffer()

        if (minute > 0) {
            sb.append(minute).append("分")
        }
        if (second > 0) {
            sb.append(second).append("秒")
        }

        return sb.toString()
    }

    /**
     * 获取前n天日期、后n天日期
     * 格式：yyyy-MM-dd
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     */
    fun getBeforeOrAfterDate(distanceDay: Int, pattern: String): String {
        val dft = SimpleDateFormat(pattern, Locale.CHINA)
        val beginDate = Date()
        val date = Calendar.getInstance()
        date.time = beginDate
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay)
        var endDate: Date? = null
        try {
            endDate = dft.parse(dft.format(date.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return dft.format(endDate)
    }

    /**
     * 根据当前日期获得是星期几
     * 格式：yyyy-MM-dd
     */
    fun getWeek(time: String, pattern: String): String {
        var Week = ""
        val format = SimpleDateFormat(pattern, Locale.CHINA)
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val wek = c.get(Calendar.DAY_OF_WEEK)
        if (wek == 1) {
            Week += "周天"
        }
        if (wek == 2) {
            Week += "周一"
        }
        if (wek == 3) {
            Week += "周二"
        }
        if (wek == 4) {
            Week += "周三"
        }
        if (wek == 5) {
            Week += "周四"
        }
        if (wek == 6) {
            Week += "周五"
        }
        if (wek == 7) {
            Week += "周六"
        }
        return Week
    }
}























