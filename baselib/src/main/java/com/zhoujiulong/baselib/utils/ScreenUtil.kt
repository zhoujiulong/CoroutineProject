package com.zhoujiulong.baselib.utils

import android.Manifest.permission.WRITE_SETTINGS
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.*
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.ScrollView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import java.lang.reflect.Field

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : utils about screen
</pre> *
 */
object ScreenUtil {

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    val screenWidth: Int
        get() {
            val wm =
                ContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                wm.defaultDisplay.getRealSize(point)
            } else {
                wm.defaultDisplay.getSize(point)
            }
            return point.x
        }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    val screenHeight: Int
        get() {
            val wm = ContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                wm.defaultDisplay.getRealSize(point)
            } else {
                wm.defaultDisplay.getSize(point)
            }
            return point.y
        }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(activity: Activity): Int {
        var statusBarHeight = 0
        //尝试第一种获取方式
        val resourceId =
            activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
            if (statusBarHeight > 0) {
                return statusBarHeight
            }
        }
        if (statusBarHeight <= 0) {
            //第一种失败时, 尝试第二种获取方式
            val rectangle = Rect()
            val window = activity.window
            window.decorView.getWindowVisibleDisplayFrame(rectangle)
            statusBarHeight = rectangle.top
            if (statusBarHeight > 0) {
                return statusBarHeight
            }
        }
        if (statusBarHeight <= 0) {
            try {
                var c: Class<*>? = null
                var obj: Any? = null
                var field: Field? = null
                var x = 0
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c!!.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field!!.get(obj).toString())
                statusBarHeight = activity.resources.getDimensionPixelSize(x)
                return statusBarHeight
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

        }
        return DensityUtil.dip2px(activity, 24f)
    }

    /**
     * 获取导航栏高度
     */
    fun getNavigationHeight(context: Context): Int {
        var resourceId = 0
        val rid = context.resources.getIdentifier("config_showNavigationBar", "bool", "android")
        if (rid != 0) {
            resourceId =
                context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    /**
     * 是否存在导航栏
     */
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        if (context is Activity) {
            val windowManager = context.windowManager
            val d = windowManager.defaultDisplay

            val realDisplayMetrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                d.getRealMetrics(realDisplayMetrics)
            }

            val realHeight = realDisplayMetrics.heightPixels
            val realWidth = realDisplayMetrics.widthPixels

            val displayMetrics = DisplayMetrics()
            d.getMetrics(displayMetrics)

            val displayHeight = displayMetrics.heightPixels
            val displayWidth = displayMetrics.widthPixels

            return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
        }
        return false
    }

    /**
     * Return the density of screen.
     *
     * @return the density of screen
     */
    val screenDensity: Float
        get() = ContextUtil.getContext().resources.displayMetrics.density

    /**
     * Return the screen density expressed as dots-per-inch.
     *
     * @return the screen density expressed as dots-per-inch
     */
    val screenDensityDpi: Int
        get() = ContextUtil.getContext().resources.displayMetrics.densityDpi

    /**
     * Set full screen.
     *
     * @param activity The activity.
     */
    fun setFullScreen(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /**
     * Set non full screen.
     *
     * @param activity The activity.
     */
    fun setNonFullScreen(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /**
     * Toggle full screen.
     *
     * @param activity The activity.
     */
    fun toggleFullScreen(activity: Activity) {
        val fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        val window = activity.window
        if (window.attributes.flags and fullScreenFlag == fullScreenFlag) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The activity.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isFullScreen(activity: Activity): Boolean {
        val fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        return activity.window.attributes.flags and fullScreenFlag == fullScreenFlag
    }

    /**
     * Set the screen to landscape.
     *
     * @param activity The activity.
     */
    fun setLandscape(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    /**
     * Set the screen to portrait.
     *
     * @param activity The activity.
     */
    fun setPortrait(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * Return whether screen is landscape.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    val isLandscape: Boolean
        get() = ContextUtil.getContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    /**
     * Return whether screen is portrait.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    val isPortrait: Boolean
        get() = ContextUtil.getContext().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    /**
     * Return the rotation of screen.
     *
     * @param activity The activity.
     * @return the rotation of screen
     */
    fun getScreenRotation(activity: Activity): Int {
        when (activity.windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> return 0
            Surface.ROTATION_90 -> return 90
            Surface.ROTATION_180 -> return 180
            Surface.ROTATION_270 -> return 270
            else -> return 0
        }
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    @JvmOverloads
    fun screenShot(activity: Activity, isDeleteStatusBar: Boolean = false): Bitmap {
        val decorView = activity.window.decorView
        decorView.isDrawingCacheEnabled = true
        decorView.buildDrawingCache()
        val bmp = decorView.drawingCache
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        val ret: Bitmap
        if (isDeleteStatusBar) {
            val resources = activity.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = resources.getDimensionPixelSize(resourceId)
            ret = Bitmap.createBitmap(
                bmp,
                0,
                statusBarHeight,
                dm.widthPixels,
                dm.heightPixels - statusBarHeight
            )
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
        }
        decorView.destroyDrawingCache()
        return ret
    }

    /**
     * 获取控件的截图
     */
    fun screenShotView(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val ret = Bitmap.createBitmap(bmp, 0, 0, view.measuredWidth, view.measuredHeight)
        view.destroyDrawingCache()
        return ret
    }

    /**
     * 获取控件的截图
     *
     * @param backgroundColorRes 背景颜色
     * @param radius             背景圆角大小
     */
    fun screenShotView(scrollView: ScrollView, @ColorRes backgroundColorRes: Int, @DimenRes radius: Int): Bitmap {
        var h = 0
        val bitmap: Bitmap
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
        }
        h = if (h > scrollView.measuredHeight) h else scrollView.measuredHeight
        bitmap = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        val rectF = RectF(0f, 0f, scrollView.width.toFloat(), h.toFloat())
        val rads = DensityUtil.getPxByResId(ContextUtil.getContext(), radius)
        val paint = Paint()
        paint.color = ContextCompat.getColor(ContextUtil.getContext(), backgroundColorRes)
        canvas.drawRoundRect(rectF, rads.toFloat(), rads.toFloat(), paint)
        scrollView.draw(canvas)
        return bitmap
    }

    /**
     * Return whether screen is locked.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    val isScreenLock: Boolean
        get() {
            val km =
                ContextUtil.getContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            return km != null && km.inKeyguardRestrictedInputMode()
        }

    /**
     * Return the duration of sleep.
     *
     * @return the duration of sleep.
     */
    /**
     * Set the duration of sleep.
     *
     * Must hold `<uses-permission android:name="android.permission.WRITE_SETTINGS" />`
     *
     * @param duration The duration.
     */
    var sleepDuration: Int
        get() {
            try {
                return Settings.System.getInt(
                    ContextUtil.getContext().contentResolver,
                    Settings.System.SCREEN_OFF_TIMEOUT
                )
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return -123
            }

        }
        @RequiresPermission(WRITE_SETTINGS)
        set(duration) {
            Settings.System.putInt(
                ContextUtil.getContext().contentResolver,
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
            )
        }

    /**
     * Return whether device is tablet.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    val isTablet: Boolean
        get() = ContextUtil.getContext().resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE

    /**
     * Adapt the screen for vertical slide.
     *
     * @param designWidthInDp The size of design diagram's width, in dp,
     * e.g. the design diagram width is 720px, in XHDPI device,
     * the designWidthInDp = 720 / 2.
     */
    fun adaptScreen4VerticalSlide(
        activity: Activity,
        designWidthInDp: Int
    ) {
        adaptScreen(activity, designWidthInDp.toFloat(), true)
    }

    /**
     * Adapt the screen for horizontal slide.
     *
     * @param designHeightInDp The size of design diagram's height, in dp,
     * e.g. the design diagram height is 1080px, in XXHDPI device,
     * the designHeightInDp = 1080 / 3.
     */
    fun adaptScreen4HorizontalSlide(
        activity: Activity,
        designHeightInDp: Int
    ) {
        adaptScreen(activity, designHeightInDp.toFloat(), false)
    }

    /**
     * Cancel adapt the screen.
     *
     * @param activity The activity.
     */
    fun cancelAdaptScreen(activity: Activity) {
        val appDm = ContextUtil.getContext().resources.displayMetrics
        val activityDm = activity.resources.displayMetrics
        activityDm.density = appDm.density
        activityDm.scaledDensity = appDm.scaledDensity
        activityDm.densityDpi = appDm.densityDpi
    }

    /**
     * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     */
    private fun adaptScreen(
        activity: Activity,
        sizeInDp: Float,
        isVerticalSlide: Boolean
    ) {
        val appDm = ContextUtil.getContext().resources.displayMetrics
        val activityDm = activity.resources.displayMetrics
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / sizeInDp
        } else {
            activityDm.density = activityDm.heightPixels / sizeInDp
        }
        activityDm.scaledDensity = activityDm.density * (appDm.scaledDensity / appDm.density)
        activityDm.densityDpi = (160 * activityDm.density).toInt()
    }
}
