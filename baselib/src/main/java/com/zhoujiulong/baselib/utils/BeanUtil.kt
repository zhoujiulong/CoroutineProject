package com.zhoujiulong.baselib.utils

/**
 * @author zhoujiulong
 * @createtime 2019/4/4 13:59
 */
object BeanUtil {

    /**
     * 复制对象
     */
    fun <T> cloneBean(obj: Any, cls: Class<T>): T {
        val jsonObject = JsonUtil.object2String(obj)
        return JsonUtil.jsonToBean(jsonObject, cls)
    }

}












