package com.zhoujiulong.baselib.utils

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.zhoujiulong.baselib.R

/**
 * Toast工具类
 */
@SuppressLint("StaticFieldLeak")
object ToastUtil {

    private var mToast: Toast? = null
    private var mTvMsg: TextView? = null

    /**
     * 显示一个Toast
     *
     * @param strResId 字符串资源ID
     */
    fun toastLong(strResId: Int) {
        toast(strResId, Toast.LENGTH_LONG)
    }

    /**
     * 显示一个Toast
     *
     * @param msg 信息
     */
    fun toastLong(msg: String) {
        toast(msg, Toast.LENGTH_LONG)
    }

    /**
     * 显示一个Toast
     *
     * @param strResId 字符串资源ID
     * @param time     显示时间
     */
    @JvmOverloads
    fun toast(strResId: Int, time: Int = Toast.LENGTH_SHORT) {
        toast(ContextUtil.getContext().resources.getString(strResId), time)
    }

    /**
     * 显示一个Toast
     *
     * @param msg  信息
     * @param time 显示时间
     */
    @JvmOverloads
    fun toast(msg: String, time: Int = Toast.LENGTH_SHORT) {
        if (mToast == null) {
            val v =
                LayoutInflater.from(ContextUtil.getContext()).inflate(R.layout.base_layout_toast, null)
            mTvMsg = v.findViewById(R.id.tv_msg)
            mToast = Toast(ContextUtil.getContext())
            mToast!!.setGravity(Gravity.CENTER, 0, 0)
            mToast!!.view = v
            mToast!!.view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) {
                    //弹窗消失，置为空，不然会出现不弹出提示
                    mToast = null
                    mTvMsg = null
                }

                override fun onViewDetachedFromWindow(view: View) {}
            })
        }
        mTvMsg!!.text = msg
        mToast!!.duration = time
        mToast!!.show()
    }

    /**
     * 成功提示
     */
    fun showSuccess(msg: String) {
        val v =
            LayoutInflater.from(ContextUtil.getContext()).inflate(R.layout.base_layout_state_toast, null)
        val tvMsg = v.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = msg
        tvMsg.visibility = if (android.text.TextUtils.isEmpty(msg)) View.GONE else View.VISIBLE
        val ivIcon = v.findViewById<ImageView>(R.id.iv_icon)
        ivIcon.setImageResource(R.mipmap.ic_lib_toastsuccess)
        val toast = Toast(ContextUtil.getContext())
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.view = v
        toast.show()
    }

    /**
     * 失败提示
     */
    fun showFaild(msg: String) {
        val v =
            LayoutInflater.from(ContextUtil.getContext()).inflate(R.layout.base_layout_state_toast, null)
        val toast = Toast(ContextUtil.getContext())
        val tvMsg = v.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = msg
        tvMsg.visibility = if (android.text.TextUtils.isEmpty(msg)) View.GONE else View.VISIBLE
        val ivIcon = v.findViewById<ImageView>(R.id.iv_icon)
        ivIcon.setImageResource(R.mipmap.ic_lib_tost_failed)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.view = v
        toast.show()
    }
}






















