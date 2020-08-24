package com.zhoujiulong.baselib.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import android.text.TextUtils

/**
 * 手机网络相关工具类
 */
object NetworkUtil {


    /**
     * wifi
     */
    val NETWORK_TYPE_WIFI = "wifi"

    /**
     * wap
     */
    val NETWORK_TYPE_WAP = "wap"

    /**
     * 2G
     */
    val NETWORK_TYPE_2G = "2g"

    /**
     * 3G
     */
    val NETWORK_TYPE_3G = "3g"

    /**
     * 4G
     */
    val NETWORK_TYPE_4G = "4g"

    /**
     * Get the network info
     *
     * @param context Context
     * @return NetworkInfo
     */
    fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * 判断当前网络是否可用
     *
     * @param ctx Context
     * @return true-可用，false-不可用
     */
    fun isNetworkAvailable(ctx: Context): Boolean {
        val info = getNetworkInfo(ctx)
        return info != null && info.isConnected
    }

    /**
     * 跳转到网络设置页面
     *
     * @param ctx Context
     */
    fun jumpToNetworkSettingPage(ctx: Context) {
        // 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
        ctx.startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
    }

    /**
     * 获取通过mobile连接方式时的连接类型名称。例如：[.NETWORK_TYPE_4G]
     *
     * @param context Context
     * @return String
     */
    fun getMobileNetworkTypeName(context: Context): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        when (telephonyManager.networkType) {
            TelephonyManager.NETWORK_TYPE_1xRTT -> return NETWORK_TYPE_2G // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_CDMA -> return NETWORK_TYPE_2G // ~ 14-64 kbps
            TelephonyManager.NETWORK_TYPE_EDGE -> return NETWORK_TYPE_2G // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> return NETWORK_TYPE_3G // ~ 400-1000 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_A -> return NETWORK_TYPE_3G // ~ 600-1400 kbps
            TelephonyManager.NETWORK_TYPE_GPRS -> return NETWORK_TYPE_2G // ~ 100 kbps
            TelephonyManager.NETWORK_TYPE_HSDPA -> return NETWORK_TYPE_3G // ~ 2-14 Mbps
            TelephonyManager.NETWORK_TYPE_HSPA -> return NETWORK_TYPE_3G // ~ 700-1700 kbps
            TelephonyManager.NETWORK_TYPE_HSUPA -> return NETWORK_TYPE_3G // ~ 1-23 Mbps
            TelephonyManager.NETWORK_TYPE_UMTS -> return NETWORK_TYPE_3G // ~ 400-7000 kbps
            TelephonyManager.NETWORK_TYPE_EHRPD -> return NETWORK_TYPE_3G // ~ 1-2 Mbps
            TelephonyManager.NETWORK_TYPE_EVDO_B -> return NETWORK_TYPE_3G // ~ 5 Mbps
            TelephonyManager.NETWORK_TYPE_HSPAP -> return NETWORK_TYPE_4G // ~ 10-20 Mbps
            TelephonyManager.NETWORK_TYPE_IDEN -> return NETWORK_TYPE_2G // ~25 kbps
            TelephonyManager.NETWORK_TYPE_LTE -> return NETWORK_TYPE_4G // ~ 10+ Mbps
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> return NETWORK_TYPE_2G
            else -> return NETWORK_TYPE_2G
        }
    }
}
