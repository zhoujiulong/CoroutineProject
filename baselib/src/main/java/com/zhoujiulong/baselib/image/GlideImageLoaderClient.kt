package com.zhoujiulong.baselib.image

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zhoujiulong.baselib.image.bean.ImageSize
import com.zhoujiulong.baselib.image.listener.IGetBitmapListener
import com.zhoujiulong.baselib.image.listener.IGetDrawableListener
import com.zhoujiulong.baselib.image.tranform.BlurBitmapTranformation
import java.io.File


/**
 * Created by shiming on 2016/10/26.
 * des:
 * with(Context context). 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制。
 * with(Activity activity).使用Activity作为上下文，Glide的请求会受到Activity生命周期控制。
 * with(FragmentActivity activity).Glide的请求会受到FragmentActivity生命周期控制。
 * with(android.app.Fragment fragment).Glide的请求会受到Fragment 生命周期控制。
 * with(android.support.v4.app.Fragment fragment).Glide的请求会受到Fragment生命周期控制。
 */

class GlideImageLoaderClient : IImageLoaderClient {

    override fun init(context: Context) {}

    override fun getCacheDir(context: Context): File {
        return Glide.getPhotoCacheDir(context)!!
    }

    @UiThread
    override fun clearMemoryCache(context: Context) {
        Glide.get(context).clearMemory()
    }

    @SuppressLint("StaticFieldLeak")
    override fun clearDiskCache(context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                //必须在子线程中  This method must be called on a background thread.
                Glide.get(context).clearDiskCache()
                return null
            }
        }
    }

    /**
     * 获取缓存中的图片
     */
    override fun getBitmapFromCache(context: Context, url: String, listener: IGetBitmapListener) {
        Glide.with(context).asBitmap().load(url).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                listener?.onBitmap(resource)
            }
        })
    }

    override fun displayImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    override fun displayImage(fragment: Fragment, url: String, imageView: ImageView) {
        Glide.with(fragment).load(url).into(imageView)
    }

    override fun displayImage(activity: Activity, url: String, imageView: ImageView) {
        Glide.with(activity).load(url).into(imageView)
    }


    override fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    ) {
        Glide.with(context).load(url).placeholder(placeholderResId).error(errorResId)
            .into(imageView)
    }

    override fun displayImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    ) {
        Glide.with(fragment).load(url).placeholder(placeholderResId).error(errorResId)
            .into(imageView)
    }

    override fun displayImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int
    ) {
        Glide.with(activity).load(url).placeholder(placeholderResId).error(errorResId)
            .into(imageView)
    }

    override fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    ) {
        Glide.with(context).load(url).placeholder(placeholderResId).error(errorResId)
            .override(size.width, size.height).into(imageView)
    }

    override fun displayImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    ) {
        Glide.with(fragment).load(url).placeholder(placeholderResId).error(errorResId)
            .override(size.width, size.height).into(imageView)
    }

    override fun displayImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        size: ImageSize
    ) {
        Glide.with(activity).load(url).placeholder(placeholderResId).error(errorResId)
            .override(size.width, size.height).into(imageView)
    }

    override fun displayBlurImage(
        context: Context,
        resId: Int,
        blurRadius: Int,
        listener: IGetDrawableListener
    ) {
        Glide.with(context).load(resId).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                listener?.onDrawable(resource)
            }
        })
    }

    override fun displayBlurImage(
        context: Context,
        url: String,
        blurRadius: Int,
        listener: IGetDrawableListener
    ) {
        Glide.with(context).load(url).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                listener?.onDrawable(resource)
            }
        })
    }

    override fun displayBlurImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    ) {
        Glide.with(context).load(url).placeholder(placeholderResId).error(errorResId)
            .transform(BlurBitmapTranformation(blurRadius)).into(imageView)
    }

    override fun displayBlurImage(
        fragment: Fragment,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    ) {
        Glide.with(fragment).load(url).placeholder(placeholderResId).error(errorResId)
            .transform(BlurBitmapTranformation(blurRadius)).into(imageView)
    }

    override fun displayBlurImage(
        activity: Activity,
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        errorResId: Int,
        blurRadius: Int
    ) {
        Glide.with(activity).load(url).placeholder(placeholderResId).error(errorResId)
            .transform(BlurBitmapTranformation(blurRadius)).into(imageView)
    }

}
