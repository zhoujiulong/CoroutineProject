package com.zhoujiulong.baselib.utils

import android.text.TextUtils
import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.util.regex.Pattern

/**
 * Created by win7 on 2019/3/13.
 */

object StringUtils {

    fun isMobileNO(mobileNums: String): Boolean {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        val telRegex =
            "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$"// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return if (TextUtils.isEmpty(mobileNums))
            false
        else
            mobileNums.matches(telRegex.toRegex())
    }

    /**
     * 判断必须包含数字字母
     */
    fun isPw(pw: String): Boolean {
        val reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$"
        return if (TextUtils.isEmpty(pw))
            false
        else
            pw.matches(reg.toRegex())
    }

    /**
     * 只含有汉字、数字、字母、下划线不能以下划线开头和结尾
     */
    fun userNameMatches(matche: String): Boolean {
        val format = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,11}"
        return if (TextUtils.isEmpty(matche))
            false
        else
            matche.matches(format.toRegex())
    }

    /**
     * 判断只包含常规字符
     */
    fun isNormalText(str: String): Boolean {
        if (TextUtils.isEmpty(str)) return true
        val pattern =
            Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]")
        val matcher = pattern.matcher(str)
        return !matcher.find()
    }

    fun isEmojiCharacter(codePoint: Char): Boolean {
        return !(codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF) || codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF
    }

    /**
     * 格式化金额并返回元或者万元
     * 清除小数点后无用的0，如果小数点后全是0则去掉小数点
     *
     * @param money 金额（分）
     * @return 小数点后无0
     */
    fun clearZeroFormatMoney(money: Long?): String {
        if (money == null) return "0"
        var moneyResult = formatMoney(money)
        if (moneyResult.indexOf(".") > 0) {
            //正则表达
            moneyResult = moneyResult.replace("0+?$".toRegex(), "")//去掉后面无用的零
            moneyResult = moneyResult.replace("[.]$".toRegex(), "")//如小数点后面全是零则去掉小数点
        }
        return moneyResult
    }

    /**
     * 格式化金额并返回元或者万元
     *
     * @param money 金额（分）
     * @return 0.00 两位小数
     */
    fun formatMoney(money: Long?): String {
        if (money == null) return "0.00"
        var bigDecimalMoney = BigDecimal(money)
        bigDecimalMoney = bigDecimalMoney.divide(BigDecimal(100), MathContext.DECIMAL64)
        val df = DecimalFormat("0.00")
        return df.format(bigDecimalMoney)
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    fun bigDecimal(v: String, scale: Int): String {
        require(scale >= 0) { "保留的小数位数必须大于零" }
        val b = BigDecimal(v)
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString()
    }

}
