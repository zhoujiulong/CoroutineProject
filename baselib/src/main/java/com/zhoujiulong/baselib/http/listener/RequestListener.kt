package com.zhoujiulong.baselib.http.listener

import com.zhoujiulong.baselib.http.other.RequestErrorType

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/15
 * 描述 :
 */

abstract class RequestListener<T> {

    /**
     * 返回bean的回调：data字段是javabean的时候使用
     *
     * @param data 返回结果中的数据实体类
     */
    abstract fun requestSuccess(data: T)

    /**
     * 请求出错回调
     *
     * @param data      返回结果中的数据实体类
     * @param type 错误类型
     * @param msg  错误信息
     * @param code 错误码
     */
    abstract fun requestError(data: T?, type: RequestErrorType, msg: String, code: Int)

    /**
     * 登录失效了就会发生这种行为
     *
     * @param code errorCode
     * @param msg   错误信息
     * @return 是否在调用的地方处理了token失效，如果返回true，在application中的初始化配置不再调用
     */
    fun checkLogin(code: Int, msg: String): Boolean {
        return false
    }
}













