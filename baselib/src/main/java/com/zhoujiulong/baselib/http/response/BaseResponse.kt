package com.zhoujiulong.baselib.http.response

import com.zhoujiulong.baselib.http.other.CodeConstant
import java.io.Serializable

/**
 * 后台数据返回的公共部分
 * Created by 0169670 on 2017/1/9.
 */
open class BaseResponse : Serializable {

    var code: Int = CodeConstant.DEFAULT_CODE
    var message: String = ""

}