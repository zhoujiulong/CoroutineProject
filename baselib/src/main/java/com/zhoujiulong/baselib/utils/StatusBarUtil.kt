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

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.IntDef
import androidx.core.view.ViewCompat

/**
 * @author cginechen
 * @date 2016-03-27
 */
object StatusBarUtil {

    private const val STATUSBAR_TYPE_DEFAULT = 0
    private const val STATUSBAR_TYPE_MIUI = 1
    private const val STATUSBAR_TYPE_FLYME = 2
    private const val STATUSBAR_TYPE_ANDROID6 = 3 // Android 6.0
    private const val STATUSBAR_NO_MATCH = 4

    @StatusBarType
    private var mStatuBarType = STATUSBAR_TYPE_DEFAULT

    /**
     * 更改状态栏图标、文字颜色的方案是否是MIUI自家的， MIUI9 && Android 6 之后用回Android原生实现
     * 见小米开发文档说明：https://dev.mi.com/console/doc/detail?pId=1159
     */
    private val isMIUICustomStatusBarLightModeImpl: Boolean
        get() = if (DeviceUtil.isMIUIV9 && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            true
        } else DeviceUtil.isMIUIV5 || DeviceUtil.isMIUIV6 ||
                DeviceUtil.isMIUIV7 || DeviceUtil.isMIUIV8

    @IntDef(
        STATUSBAR_TYPE_DEFAULT,
        STATUSBAR_NO_MATCH,
        STATUSBAR_TYPE_MIUI,
        STATUSBAR_TYPE_FLYME,
        STATUSBAR_TYPE_ANDROID6
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    private annotation class StatusBarType

    fun canTranslucent(): Boolean {
        return try {
            // Essential Phone 在 Android 8 之前沉浸式做得不全，系统不从状态栏顶部开始布局却会下发 WindowInsets，无语系列：ZTK C2016只能时间和电池图标变色。。。。
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !(DeviceUtil.isEssentialPhone && Build.VERSION.SDK_INT < 26) && supportTransclentStatusBar6()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 设置沉浸式状态栏，安卓6.0以上
     */
    fun translucent(activity: Activity): Boolean {
        try {
            if (canTranslucent()) {
                val window = activity.window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    handleDisplayCutoutMode(window)
                }
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = Color.TRANSPARENT
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                }
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 设置状态栏黑色字体图标，
     *
     * @param activity 需要被处理的 Activity
     */
    fun setStatusBarLightMode(activity: Activity?): Boolean {
        try {
            if (canTranslucent()) {
                if (activity == null) return false
                // 无语系列：ZTK C2016只能时间和电池图标变色。。。。
                if (DeviceUtil.isZTKC2016) {
                    return false
                }
                if (mStatuBarType == STATUSBAR_TYPE_DEFAULT) initStatusBarType(activity)

                if (mStatuBarType == STATUSBAR_TYPE_MIUI) {
                    return MIUISetStatusBarLightMode(activity.window, true)
                } else if (mStatuBarType == STATUSBAR_TYPE_FLYME) {
                    return FlymeSetStatusBarLightMode(activity.window, true)
                } else if (mStatuBarType == STATUSBAR_TYPE_ANDROID6) {
                    return Android6SetStatusBarLightMode(activity.window, true)
                }
                return false
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 设置状态栏白色字体图标
     */
    fun setStatusBarDarkMode(activity: Activity?): Boolean {
        if (canTranslucent()) {
            if (activity == null) return false
            if (mStatuBarType == STATUSBAR_TYPE_DEFAULT) initStatusBarType(activity)

            if (mStatuBarType == STATUSBAR_TYPE_MIUI) {
                return MIUISetStatusBarLightMode(activity.window, false)
            } else if (mStatuBarType == STATUSBAR_TYPE_FLYME) {
                return FlymeSetStatusBarLightMode(activity.window, false)
            } else if (mStatuBarType == STATUSBAR_TYPE_ANDROID6) {
                return Android6SetStatusBarLightMode(activity.window, false)
            }
            return false
        } else {
            return false
        }
    }

    @TargetApi(28)
    private fun handleDisplayCutoutMode(window: Window) {
        val decorView = window.decorView
        if (ViewCompat.isAttachedToWindow(decorView)) {
            realHandleDisplayCutoutMode(window, decorView)
        } else {
            decorView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    v.removeOnAttachStateChangeListener(this)
                    realHandleDisplayCutoutMode(window, v)
                }

                override fun onViewDetachedFromWindow(v: View) {

                }
            })
        }
    }

    @TargetApi(28)
    private fun realHandleDisplayCutoutMode(window: Window, decorView: View) {
        if (decorView.rootWindowInsets != null && decorView.rootWindowInsets.displayCutout != null) {
            val params = window.attributes
            params.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = params
        }
    }

    @TargetApi(23)
    private fun changeStatusBarModeRetainFlag(window: Window, out: Int): Int {
        var out = out
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_FULLSCREEN)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        return out
    }

    private fun retainSystemUiFlag(window: Window, out: Int, type: Int): Int {
        var out = out
        val now = window.decorView.systemUiVisibility
        if (now and type == type) {
            out = out or type
        }
        return out
    }


    /**
     * 设置状态栏字体图标为深色，Android 6
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @TargetApi(23)
    private fun Android6SetStatusBarLightMode(window: Window, light: Boolean): Boolean {
        val decorView = window.decorView
        var systemUi =
            if (light) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        systemUi = changeStatusBarModeRetainFlag(window, systemUi)
        decorView.systemUiVisibility = systemUi
        if (DeviceUtil.isMIUIV9) {
            // MIUI 9 低于 6.0 版本依旧只能回退到以前的方案
            // https://github.com/Tencent/QMUI_Android/issues/160
            MIUISetStatusBarLightMode(window, light)
        }
        return true
    }

    /**
     * 设置状态栏字体图标为深色，需要 MIUIV6 以上
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回 true
     */
    private fun MIUISetStatusBarLightMode(window: Window?, light: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                val darkModeFlag: Int
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                if (light) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                }
                result = true
            } catch (ignored: Exception) {

            }

        }
        return result
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为 Flyme 用户
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun FlymeSetStatusBarLightMode(window: Window?, light: Boolean): Boolean {
        var result = false
        if (window != null) {
            // flyme 在 6.2.0.0A 支持了 Android 官方的实现方案，旧的方案失效
            Android6SetStatusBarLightMode(window, light)
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (light) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (ignored: Exception) {

            }

        }
        return result
    }

    /**
     * 检测 Android 6.0 是否可以启用 window.setStatusBarColor(Color.TRANSPARENT)。
     * ZUK Z1是个另类，自家应用可以实现字体颜色变色，但没开放接口
     */
    private fun supportTransclentStatusBar6(): Boolean {
        return !(DeviceUtil.isZUKZ1 || DeviceUtil.isZTKC2016)
    }

    private fun initStatusBarType(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatuBarType = if (isMIUICustomStatusBarLightModeImpl && MIUISetStatusBarLightMode(
                    activity.window,
                    true
                )
            ) {
                STATUSBAR_TYPE_MIUI
            } else if (FlymeSetStatusBarLightMode(activity.window, true)) {
                STATUSBAR_TYPE_FLYME
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Android6SetStatusBarLightMode(activity.window, true)
                STATUSBAR_TYPE_ANDROID6
            } else {
                STATUSBAR_NO_MATCH
            }
        }
    }

}
