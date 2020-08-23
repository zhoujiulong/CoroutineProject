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
 * GlideApp 是自动生成的哦
 * 图片加载工具类
 */
class ImageLoader private constructor() : IImageLoaderClient {
    private val client: IImageLoaderClient by lazy { GlideImageLoaderClient() }

    companion object {

        @Volatile
        private var instance: ImageLoader? = null

        fun getInstance(): ImageLoader {
            if (instance == null) {
                synchronized(ImageLoader::class.java) {
                    if (instance == null) {
                        instance = ImageLoader()
                    }
                }
            }
            return instance!!
        }
    }

    override fun init(context: Context) {}

    override fun getCacheDir(context: Context): File {
        return client.getCacheDir(context)
    }

    override fun clearMemoryCache(context: Context) {
        client.clearMemoryCache(context)
    }

    override fun clearDiskCache(context: Context) {
        client.clearDiskCache(context)
    }

    override fun getBitmapFromCache(context: Context, url: String, listener: IGetBitmapListener) {
        client.getBitmapFromCache(context, url, listener)
    }

    override fun displayImage(context: Context, url: String, imageView: ImageView) {
        client.displayImage(context, url, imageView)
    }

    override fun displayImage(fragment: Fragment, url: String, imageView: ImageView) {
        client.displayImage(fragment, url, imageView)
    }

    override fun displayImage(activity: Activity, url: String, imageView: ImageView) {
        client.displayImage(activity, url, imageView)
    }

    override fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    ) {
        client.displayImage(context, url, imageView, placeholderResId, errorResId)
    }

    override fun displayImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    ) {
        client.displayImage(fragment, url, imageView, placeholderResId, errorResId)
    }

    override fun displayImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    ) {
        client.displayImage(activity, url, imageView, placeholderResId, errorResId)
    }

    override fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    ) {
        client.displayImage(context, url, imageView, placeholderResId, errorResId, size)
    }

    override fun displayImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    ) {
        client.displayImage(fragment, url, imageView, placeholderResId, errorResId, size)
    }

    override fun displayImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    ) {
        client.displayImage(activity, url, imageView, placeholderResId, errorResId, size)
    }

    override fun displayBlurImage(
        context: Context,
        resId: Int,
        blurRadius: Int,
        listener: IGetDrawableListener
    ) {
        client.displayBlurImage(context, resId, blurRadius, listener)
    }

    override fun displayBlurImage(
        context: Context,
        url: String,
        blurRadius: Int,
        listener: IGetDrawableListener
    ) {
        client.displayBlurImage(context, url, blurRadius, listener)
    }

    @Deprecated("")
    override fun displayBlurImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    ) {
        client.displayBlurImage(context, url, imageView, placeholderResId, errorResId, blurRadius)
    }

    override fun displayBlurImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    ) {
        client.displayBlurImage(
            fragment,
            url,
            imageView,
            placeholderResId,
            errorResId,
            blurRadius
        )
    }

    override fun displayBlurImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    ) {
        client.displayBlurImage(
            activity,
            url,
            imageView,
            placeholderResId,
            errorResId,
            blurRadius
        )
    }


}
