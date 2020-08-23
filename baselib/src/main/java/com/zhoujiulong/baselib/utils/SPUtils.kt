package com.zhoujiulong.baselib.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

/**
 * Created by 0169670 on 2016/11/14.
 * SharedPreferences 存储工具类
 * 除登录信息外其它地方的 key 放到主项目的 SPConstant 类中
 */
object SPUtils {

    /**
     * 文件名
     */
    private val SHAREPREFERENCES_FILENAME = "worldunion_homeplus_sp"
    /**
     * 默认模式，只允许应用本身访问数据
     */
    private val MODE = Context.MODE_PRIVATE

    /**
     * 获取 ShredPreferences
     */
    private val sharedPreferences: SharedPreferences
        get() = ContextUtil.getContext().getSharedPreferences(SHAREPREFERENCES_FILENAME, MODE)

    /**
     * 清空数据
     */
    fun clearSharePreference() {
        sharedPreferences.edit().clear().apply()
    }

    /**
     * 删除指定 SharePreferencs
     */
    fun clearSharePreferencrForName(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * 保存String
     */
    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * 获得String类型
     */
    fun getString(key: String, defValue: String): String {
        return sharedPreferences.getString(key, defValue) ?: defValue
    }

    /**
     * 存储boolean类型数据
     */
    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * 获得Boolean类型数据
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    /**
     * 保存int类型
     */
    fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * 保存long类型
     */
    fun putLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * 获得int类型
     */
    fun getInt(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    /**
     * 获得int类型
     */
    fun getLong(key: String, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    /**
     * SharedPreferences 对象数据初始化
     */
    fun putObject(key: String, `object`: Any) {
        val jsonObject = JsonUtil.object2String(`object`)
        putString(key, jsonObject)
    }

    /**
     * SharedPreferencesȡ 获取对象
     */
    fun <T> getObject(key: String, cls: Class<T>): T? {
        val json = getString(key, "")
        if (!TextUtils.isEmpty(json)) {
            try {
                return JsonUtil.jsonToBean(json, cls)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return null
    }

}
