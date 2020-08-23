package com.zhoujiulong.baselib.http.other

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/17
 * 描述 : 请求返回的 code 类型
 */

enum class RequestErrorType {
    /**
     * 未定义的错误
     */
    COMMON_ERROR,
    /**
     * 没有网络
     */
    NO_INTERNET,
    /**
     * Token 失效
     */
    TOKEN_INVALID
}
