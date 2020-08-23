/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhoujiulong.baselib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.reflect.Method
import java.util.*

/**
 * @author cginechen
 * @date 2016-08-11
 */
@SuppressLint("PrivateApi")
object DeviceUtil {
    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_FLYME_VERSION_NAME = "ro.build.display.id"
    private val FLYME = "flyme"
    private val ZTEC2016 = "zte c2016"
    private val ZUKZ1 = "zuk z1"
    private val MEIZUBOARD = arrayOf("m9", "M9", "mx", "MX")
    private var sMiuiVersionName: String? = null
    private var sFlymeVersionName: String? = null
    private var sIsTabletChecked = false
    private var sIsTabletValue = false
    private val BRAND = Build.BRAND.toLowerCase()

    init {
        val properties = Properties()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // android 8.0，读取 /system/uild.prop 会报 permission denied
            var fileInputStream: FileInputStream? = null
            try {
                fileInputStream =
                    FileInputStream(File(Environment.getRootDirectory(), "build.prop"))
                properties.load(fileInputStream)
            } catch (e: Exception) {
                LogUtil.d("read file error")
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        val clzSystemProperties: Class<*>
        try {
            clzSystemProperties = Class.forName("android.os.SystemProperties")
            val getMethod = clzSystemProperties.getDeclaredMethod("get", String::class.java)
            // miui
            sMiuiVersionName = getLowerCaseName(properties, getMethod, KEY_MIUI_VERSION_NAME)
            //flyme
            sFlymeVersionName = getLowerCaseName(properties, getMethod, KEY_FLYME_VERSION_NAME)
        } catch (e: Exception) {
            LogUtil.d("read SystemProperties error")
        }

    }

    /**
     * 判断是否是flyme系统
     */
    val isFlyme: Boolean
        get() = !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName!!.contains(FLYME)

    /**
     * 判断是否是MIUI系统
     */
    val isMIUI: Boolean
        get() = !TextUtils.isEmpty(sMiuiVersionName)

    val isMIUIV5: Boolean
        get() = "v5" == sMiuiVersionName

    val isMIUIV6: Boolean
        get() = "v6" == sMiuiVersionName

    val isMIUIV7: Boolean
        get() = "v7" == sMiuiVersionName

    val isMIUIV8: Boolean
        get() = "v8" == sMiuiVersionName

    val isMIUIV9: Boolean
        get() = "v9" == sMiuiVersionName

    val isMeizu: Boolean
        get() = isPhone(MEIZUBOARD) || isFlyme

    /**
     * 判断是否为小米
     * https://dev.mi.com/doc/?p=254
     */
    val isXiaomi: Boolean
        get() = Build.MANUFACTURER.toLowerCase() == "xiaomi"

    val isVivo: Boolean
        get() = BRAND.contains("vivo") || BRAND.contains("bbk")

    val isOppo: Boolean
        get() = BRAND.contains("oppo")

    val isHuawei: Boolean
        get() = BRAND.contains("huawei") || BRAND.contains("honor")

    val isEssentialPhone: Boolean
        get() = BRAND.contains("essential")

    /**
     * 判断是否为 ZUK Z1 和 ZTK C2016。
     * 两台设备的系统虽然为 android 6.0，但不支持状态栏icon颜色改变，因此经常需要对它们进行额外判断。
     */
    val isZUKZ1: Boolean
        get() {
            val board = Build.MODEL
            return board != null && board.toLowerCase().contains(ZUKZ1)
        }

    val isZTKC2016: Boolean
        get() {
            val board = Build.MODEL
            return board != null && board.toLowerCase().contains(ZTEC2016)
        }

    private fun _isTablet(context: Context): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    /**
     * 判断是否为平板设备
     */
    fun isTablet(context: Context): Boolean {
        if (sIsTabletChecked) {
            return sIsTabletValue
        }
        sIsTabletValue = _isTablet(context)
        sIsTabletChecked = true
        return sIsTabletValue
    }

    private fun isPhone(boards: Array<String>): Boolean {
        val board = Build.BOARD ?: return false
        for (board1 in boards) {
            if (board == board1) {
                return true
            }
        }
        return false
    }

    private fun getLowerCaseName(p: Properties, get: Method, key: String): String? {
        var name: String? = p.getProperty(key)
        if (name == null) {
            try {
                name = get.invoke(null, key) as String
            } catch (ignored: Exception) {
            }

        }
        if (name != null) name = name.toLowerCase()
        return name
    }
}
