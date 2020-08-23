package com.zhoujiulong.coroutineproject.impl

import com.zhoujiulong.baselib.http.response.DataResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url


/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:33
 * 空类，占位用
 */
interface MainService {

    @Streaming
    @GET
    fun downLoadApk(@Url fileUrl: String): Call<ResponseBody>

    @GET("test")
    fun test(@Query("searchName") searchName: String): Call<DataResponse<String>>

}
