package com.zhoujiulong.widgetlib.loadmore_refresh_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.zhoujiulong.baselib.utils.DensityUtil
import com.zhoujiulong.widgetlib.R

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Day : 2017/09/22
 * 描述 : 下拉刷新 Header
 */

@SuppressLint("RestrictedApi")
class NormalRefreshHeader : RelativeLayout, RefreshHeader {

    private var mImageViewAni: ImageView? = null
    private var mRefreshAnim: AnimationDrawable? = null
    private var mFinishDuration = 500
    private var mSpinnerStyle = SpinnerStyle.Scale
    private lateinit var mRefreshKernel: RefreshKernel

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        mImageViewAni = ImageView(context)
        mImageViewAni!!.scaleType = ImageView.ScaleType.FIT_XY
        mImageViewAni!!.setImageResource(R.drawable.widget_anim_refresh)
        val ivWidth = DensityUtil.getPxByResId(context, R.dimen.dip80)
        val ivHeight = DensityUtil.getPxByResId(context, R.dimen.dip80)
        val lpHeaderLayout = LayoutParams(ivWidth, ivHeight)
        lpHeaderLayout.addRule(CENTER_IN_PARENT)
        addView(mImageViewAni, lpHeaderLayout)

    }

    private fun initAni() {
        mRefreshAnim = mImageViewAni!!.drawable as AnimationDrawable
    }

    override fun getView(): View {
        return this
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {}

    override fun onMoving(
        isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int
    ) {
        if (percent <= 1) {
            mImageViewAni!!.scaleX = percent
            mImageViewAni!!.scaleY = percent
        }
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, extendHeight: Int) {
        if (mRefreshAnim != null) mRefreshAnim!!.stop()
    }

    override fun onStartAnimator(layout: RefreshLayout, headHeight: Int, extendHeight: Int) {
        if (mRefreshAnim == null) initAni()
        mRefreshAnim!!.start()
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        if (mRefreshAnim != null) mRefreshAnim!!.stop()
        return mFinishDuration//延迟500毫秒之后再弹回
    }

    override fun setPrimaryColors(@ColorInt vararg colors: Int) {}


    override fun getSpinnerStyle(): SpinnerStyle {
        return mSpinnerStyle
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {
        mRefreshKernel = kernel
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState
    ) {
    }

}














