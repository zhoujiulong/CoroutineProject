package com.zhoujiulong.baselib.http.response

import java.io.Serializable

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/17
 * 描述 :
 */

data class DataResponse<T>(
    val data: T
) : BaseResponse(), Serializable
