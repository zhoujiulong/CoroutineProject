package com.zhoujiulong.baselib.utils

import android.content.Context
import androidx.annotation.DimenRes

/**
 * 尺寸转换工具类
 */
object DensityUtil {

    /**
     * 根据资源文件中的配置获取对应的px
     * @param context 上下文
     * @param dimRes 资源文件中的 id
     * @return px
     */
    fun getPxByResId(context: Context, @DimenRes dimRes: Int): Int {
        return context.resources.getDimensionPixelSize(dimRes)
    }

    /**
     * @param context 上下文
     * @param dpValue dp数值
     * @return dp to  px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * @param context 上下文
     * @param pxValue px的数值
     * @return px to dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}