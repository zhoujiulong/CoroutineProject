package com.zhoujiulong.baselib.utils

import android.util.Log

import com.zhoujiulong.baselib.BuildConfig

object LogUtil {

    //打印调试开关
    private const val IS_DEBUG = BuildConfig.DEBUG_MODE
    //Log 单词打印的最大长度
    private const val MAX_LENGTH = 120
    private var mTag = "TAG"
    /**
     * 获取 TAG 信息：文件名以及行数
     *
     * @return TAG 信息
     */
    private val mMsg: String
        @Synchronized get() {
            //筛选获取需要打印的TAG
            //获取文件名以及打印的行数
            val tag = StringBuilder()
            val sts = Thread.currentThread().stackTrace ?: return ""
            tag.append("│ ")
            for (st in sts) {
                if (!st.isNativeMethod && st.className != Thread::class.java.name && st.className != LogUtil.javaClass.name) {
                    tag.append("(").append(st.fileName).append(":")
                        .append(st.lineNumber).append(")")
                    return tag.toString()
                }
            }
            return ""
        }
    private val mStartMsg by lazy {
        val builder = StringBuilder()
        builder.append("┌")
        for (i in 0..MAX_LENGTH) builder.append("─")
        builder.toString()
    }
    private val mLineMsg by lazy {
        val builder = StringBuilder()
        builder.append("├")
        for (i in 0..MAX_LENGTH) builder.append("┄")
        builder.toString()
    }
    private val mEndMsg by lazy {
        val builder = StringBuilder()
        builder.append("└")
        for (i in 0..MAX_LENGTH) builder.append("─")
        builder.toString()
    }

    fun setTag(tag: String) {
        mTag = tag
    }

    /**
     * Log.e 打印
     *
     * @param text 需要打印的内容
     */
    @Synchronized
    fun e(text: String) {
        if (IS_DEBUG) {
            Log.e(mTag, mStartMsg)
            Log.e(mTag, mMsg)
            Log.e(mTag, mLineMsg)
            for (str in splitStr(text)) {
                Log.e(mTag, str)
            }
            Log.e(mTag, mEndMsg)
        }
    }

    /**
     * Log.d 打印
     *
     * @param text 需要打印的内容
     */
    @Synchronized
    fun d(text: String) {
        if (IS_DEBUG) {
            Log.d(mTag, mStartMsg)
            Log.d(mTag, mMsg)
            Log.d(mTag, mLineMsg)
            for (str in splitStr(text)) {
                Log.d(mTag, str)
            }
            Log.d(mTag, mEndMsg)
        }
    }

    /**
     * Log.w 打印
     *
     * @param text 需要打印的内容
     */
    @Synchronized
    fun w(text: String) {
        if (IS_DEBUG) {
            Log.w(mTag, mStartMsg)
            Log.w(mTag, mMsg)
            Log.w(mTag, mLineMsg)
            for (str in splitStr(text)) {
                Log.w(mTag, str)
            }
            Log.w(mTag, mEndMsg)
        }
    }

    /**
     * Log.i 打印
     *
     * @param text 需要打印的内容
     */
    @Synchronized
    fun i(text: String) {
        if (IS_DEBUG) {
            Log.i(mTag, mStartMsg)
            Log.i(mTag, mMsg)
            Log.i(mTag, mLineMsg)
            for (str in splitStr(text)) {
                Log.i(mTag, str)
            }
            Log.i(mTag, mEndMsg)
        }
    }

    /**
     * Log.e 打印格式化后的JSON数据
     *
     * @param json 需要打印的内容
     */
    @Synchronized
    fun json(json: String) {
        if (IS_DEBUG) {
            Log.e(mTag, mStartMsg)
            Log.e(mTag, mMsg)
            Log.e(mTag, mLineMsg)
            try {
                //转化后的数据
                val logStr = formatJson(json)
                for (str in splitStr(logStr)) {
                    Log.e(mTag, str)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(mTag, e.toString())
            }
            Log.e(mTag, mEndMsg)
        }
    }

    /**
     * 数据分割成不超过 MAX_LENGTH的数据
     *
     * @param str 需要分割的数据
     * @return 分割后的数组
     */
    private fun splitStr(str: String): List<String> {
        //字符串长度
        val length = str.length
        //返回的数组
        val strList = ArrayList<String>()
        var start = 0
        val size = if (length % MAX_LENGTH == 0) length / MAX_LENGTH else length / MAX_LENGTH + 1
        for (i in 0 until size) {
            if (start + MAX_LENGTH < length) {
                strList.add("│ ${str.substring(start, start + MAX_LENGTH)}")
                start += MAX_LENGTH
            } else {
                strList.add("│ ${str.substring(start, length)}")
            }
        }
        return strList
    }


    /**
     * 格式化
     *
     * @param jsonStr json数据
     * @return 格式化后的json数据
     */
    private fun formatJson(jsonStr: String?): String {
        if (null == jsonStr || "" == jsonStr)
            return ""
        val sb = StringBuilder()
        var last: Char
        var current = '\u0000'
        var indent = 0
        var isInQuotationMarks = false
        for (element in jsonStr) {
            last = current
            current = element
            when (current) {
                '"' -> {
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks
                    }
                    sb.append(current)
                }
                '{', '[' -> {
                    sb.append(current)
                    if (!isInQuotationMarks) {
                        sb.append('\n')
                        indent++
                        addIndentBlank(sb, indent)
                    }
                }
                '}', ']' -> {
                    if (!isInQuotationMarks) {
                        sb.append('\n')
                        indent--
                        addIndentBlank(sb, indent)
                    }
                    sb.append(current)
                }
                ',' -> {
                    sb.append(current)
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }
                else -> sb.append(current)
            }
        }

        return sb.toString()
    }

    /**
     * 在 StringBuilder指定位置添加 space
     *
     * @param sb     字符集
     * @param indent 添加位置
     */
    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }
}

































