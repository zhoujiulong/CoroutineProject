package com.zhoujiulong.baselib.http.response

import java.io.Serializable

/**
 * Author : yk
 * Email : 754667445@qq.com
 * Time : 2017/08/17
 * 描述 :
 */

data class ListResponse<T>(
    val data: List<T>
) : BaseResponse(), Serializable
