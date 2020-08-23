package com.zhoujiulong.baselib.http.other

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/17
 * 描述 : 超时
 */

class TimeOut(
    /**
     * 超时类型
     */
    val timeOutType: TimeOutType,
    /**
     * 超时时间 秒
     */
    val timeOutSeconds: Long
) {

    enum class TimeOutType {
        READ_TIMEOUT,
        WRITE_TIMEOUT,
        CONNECT_TIMEOUT
    }

}





