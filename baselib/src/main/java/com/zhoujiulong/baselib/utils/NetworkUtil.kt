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
        return cm?.activeNetworkInfo
    }

    /**
     * 判断当前网络是否可用
     *
     * @param ctx Context
     * @return true-可用，false-不可用
     */
    fun isNetworkAvailable(ctx: Context): Boolean {
        val info = NetworkUtil.getNetworkInfo(ctx)
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
     * 手机是否处于 WIFI状态
     *
     * @param ctx Context
     * @return 是否是 WIFI 状态
     */
    fun isWiFi(ctx: Context): Boolean {
        val info = NetworkUtil.getNetworkInfo(ctx)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context Context
     * @return boolean
     */
    fun isConnectedMobile(context: Context): Boolean {
        val info = NetworkUtil.getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
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

    /**
     * 获取当前网络连接的类型名称
     *
     * @param context Context
     * @return 返回网络类型名称，例如：[.NETWORK_TYPE_WIFI]。<br></br>
     * 如果当前连接的类型不是wifi、mobile则返回系统定义的类型名称。
     */
    fun getNetworkTypeName(context: Context): String? {

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        var mNetWorkType: String? = null
        if (networkInfo != null && networkInfo.isConnected) {
            val type = networkInfo.type
            if (type == ConnectivityManager.TYPE_WIFI) {
                mNetWorkType = NETWORK_TYPE_WIFI
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                val proxyHost = android.net.Proxy.getDefaultHost()
                if (TextUtils.isEmpty(proxyHost)) {
                    mNetWorkType = getMobileNetworkTypeName(context)
                } else {
                    mNetWorkType = NETWORK_TYPE_WAP
                }
            } else {
                // 当前连接的类型不是wifi、mobile时，返回系统给出的类别名称
                mNetWorkType = networkInfo.typeName
            }
        }
        return mNetWorkType
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context Context
     * @return boolean
     */
    fun isConnectedFast(context: Context): Boolean {
        val info = NetworkUtil.getNetworkInfo(context)
        return info != null && info.isConnected && NetworkUtil.isConnectionFast(
            info.type,
            info.subtype
        )
    }

    /**
     * Check if the connection is fast
     *
     * @param type    int
     * @param subType int
     * @return boolean
     */
    private fun isConnectionFast(type: Int, subType: Int): Boolean {
        return when (type) {
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> when (subType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
                TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
                TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
                TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
                TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
                TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
                TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps
                /*
                         * Above API level 7, make sure to set android:targetSdkVersion to appropriate level to use these
                         */
                TelephonyManager.NETWORK_TYPE_EHRPD // API level 11
                -> true // ~ 1-2 Mbps
                TelephonyManager.NETWORK_TYPE_EVDO_B // API level 9
                -> true // ~ 5 Mbps
                TelephonyManager.NETWORK_TYPE_HSPAP // API level 13
                -> true // ~ 10-20 Mbps
                TelephonyManager.NETWORK_TYPE_IDEN // API level 8
                -> false // ~25 kbps
                TelephonyManager.NETWORK_TYPE_LTE // API level 11
                -> true // ~ 10+ Mbps
                // Unknown
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
                else -> false
            }
            else -> false
        }
    }

}
