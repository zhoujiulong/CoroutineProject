package com.zhoujiulong.baselib.http

import retrofit2.Call
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : HttpUtil 辅助类，管理和取消请求
 */
internal class RequestManager private constructor() {

    companion object {

        private var mInstance: RequestManager? = null

        /**
         * 保存没有完成的请求
         */
        private val mCallMap = ConcurrentHashMap<String, ArrayList<Call<*>>>()

        val instance: RequestManager
            get() {
                if (mInstance == null) {
                    synchronized(RequestManager::class.java) {
                        if (mInstance == null) mInstance = RequestManager()
                    }
                }
                return mInstance!!
            }
    }

    /**
     * 是否还有请求
     */
    @Synchronized
    fun hasRequest(reTag: String): Boolean {
        return mCallMap.containsKey(reTag) && mCallMap[reTag]?.size ?: -1 > 0
    }

    /**
     * 根据请求的标记 reTag 取消请求
     */
    @Synchronized
    fun cancelRequestWithTag(reTag: String) {
        if (mCallMap.containsKey(reTag)) {
            val callList = mCallMap[reTag]
            if (callList != null && callList.isNotEmpty()) {
                for (call in callList) {
                    if (!call.isCanceled) call.cancel()
                }
                callList.clear()
            }
            mCallMap.remove(reTag)
        }
    }

    /**
     * 发送请求，将请求添加到 map 中进行保存
     */
    @Synchronized
    fun addCall(reTag: String, call: Call<*>) {
        if (mCallMap.containsKey(reTag) && mCallMap[reTag] != null) {
            mCallMap[reTag]!!.add(call)
        } else {
            val callList = ArrayList<Call<*>>()
            callList.add(call)
            mCallMap[reTag] = callList
        }
    }

    /**
     * 请求结束后，将请求从 map 中移除
     */
    @Synchronized
    fun removeCall(reTag: String, call: Call<*>) {
        if (mCallMap.containsKey(reTag)) {
            val callList = mCallMap[reTag]
            if (callList != null && callList.contains(call)) {
                callList.remove(call)
            }
            if (callList.isNullOrEmpty()) {
                mCallMap.remove(reTag)
            }
        }
    }

}
















