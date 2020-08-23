package com.zhoujiulong.baselib.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * Created by zengwendi on 2017/7/18.
 */

@SuppressLint("StaticFieldLeak")
object ContextUtil {

    @SuppressLint("StaticFieldLeak")
    private lateinit var mContext: Context

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    fun init(context: Context) {
        this.mContext = context.applicationContext
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    fun getContext(): Context {
        return mContext
    }
}
