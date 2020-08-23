package com.zhoujiulong.baselib.image

import android.app.Activity
import android.content.Context
import android.widget.ImageView

import androidx.fragment.app.Fragment

import com.zhoujiulong.baselib.image.bean.ImageSize
import com.zhoujiulong.baselib.image.listener.IGetBitmapListener
import com.zhoujiulong.baselib.image.listener.IGetDrawableListener

import java.io.File


/**
 * Created by shiming on 2016/10/26.
 */

interface IImageLoaderClient {

    fun init(context: Context)

    fun getCacheDir(context: Context): File

    fun clearMemoryCache(context: Context)

    fun clearDiskCache(context: Context)

    fun getBitmapFromCache(context: Context, url: String, listener: IGetBitmapListener)

    fun displayImage(context: Context, url: String, imageView: ImageView)

    fun displayImage(fragment: Fragment, url: String, imageView: ImageView)

    fun displayImage(activity: Activity, url: String, imageView: ImageView)

    fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    )

    fun displayImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    )

    fun displayImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    )

    fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    )

    fun displayImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    )

    fun displayImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    )

    fun displayBlurImage(
        context: Context,
        resId: Int,
        blurRadius: Int,
        listener: IGetDrawableListener
    )

    fun displayBlurImage(
        context: Context,
        url: String,
        blurRadius: Int,
        listener: IGetDrawableListener
    )

    fun displayBlurImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    )

    fun displayBlurImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    )

    fun displayBlurImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    )

}
