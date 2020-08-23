package com.zhoujiulong.baselib.http.listener

/**
 * @author zhoujiulong
 * @createtime 2019/2/28 9:15
 * Token 失效回调
 */
interface OnTokenInvalidListener {

    fun onTokenInvalid(code: Int, msg: String)

}


















