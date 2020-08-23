package com.zhoujiulong.baselib.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/16
 * 描述 : JSON工具类
 */

object JsonUtil {

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json String
     * @param cls  Class
     * @param <T>  T
     * @return List
    </T> */
    fun <T> jsonToList(json: String, cls: Class<T>): List<T> {
        val list = ArrayList<T>()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(Gson().fromJson(elem, cls))
        }
        return list
    }


    /**
     * 转成json
     *
     * @param obj Object
     * @return String
     */
    fun object2String(obj: Any): String {
        return Gson().toJson(obj)
    }

    /**
     * 转成bean
     *
     * @param str String
     * @param cls        T
     * @return T
     */
    fun <T> jsonToBean(str: String, cls: Class<T>): T {
        return Gson().fromJson(str, cls)
    }


    /**
     * 转成list中有map的
     *
     * @param str String
     * @return List
     */
    fun <T> jsonToListMaps(str: String): List<Map<String, T>>? {
        return Gson().fromJson<List<Map<String, T>>>(
            str,
            object : TypeToken<List<Map<String, T>>>() {}.type
        )
    }

    /**
     * 转成map的
     *
     * @param str String
     * @return Map
     */
    fun <T> jsonToMaps(str: String): Map<String, T>? {
        return Gson().fromJson<Map<String, T>>(str, object : TypeToken<Map<String, T>>() {
        }.type)
    }

}

















