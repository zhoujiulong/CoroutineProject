package com.zhoujiulong.widgetlib.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.zhoujiulong.widgetlib.R

/**
 * @author zhoujiulong
 * @createtime 2019/3/7 14:03
 */
class CommonDialog private constructor(
    private val mContext: Context,
    themeResId: Int = R.style.WidgetCommonDialog
) : Dialog(mContext, themeResId), View.OnClickListener {

    private val mTvMsg: TextView
    private val mTvLeftBt: TextView
    private val mTvRightBt: TextView
    private val mViewLine: View
    private var mListener: Listener? = null
    private var mIsAutoDismiss = true

    init {
        setContentView(R.layout.widget_dialog_common)

        mTvMsg = findViewById(R.id.tv_msg)
        mTvLeftBt = findViewById(R.id.tv_left)
        mTvRightBt = findViewById(R.id.tv_right)
        mViewLine = findViewById(R.id.view_center_line)

        mTvLeftBt.setOnClickListener(this)
        mTvRightBt.setOnClickListener(this)
    }

    fun setMsg(msg: CharSequence): CommonDialog {
        mTvMsg.text = msg
        return this
    }

    fun setLeftBtStr(leftBtStr: String): CommonDialog {
        mTvLeftBt.text = leftBtStr
        if (TextUtils.isEmpty(leftBtStr)) {
            mTvLeftBt.visibility = View.GONE
            mViewLine.visibility = View.GONE
        } else {
            mTvLeftBt.visibility = View.VISIBLE
            mViewLine.visibility = View.VISIBLE
        }
        return this
    }

    fun setRightBtStr(rightBtStr: String): CommonDialog {
        mTvRightBt.text = rightBtStr
        if (TextUtils.isEmpty(rightBtStr)) {
            mTvRightBt.visibility = View.GONE
            mViewLine.visibility = View.GONE
        } else {
            mTvRightBt.visibility = View.VISIBLE
            mViewLine.visibility = View.VISIBLE
        }
        return this
    }

    fun setListener(listener: Listener): CommonDialog {
        mListener = listener
        return this
    }

    fun setMsgTextColorRes(@ColorRes colorRes: Int): CommonDialog {
        return setMsgTextColor(ContextCompat.getColor(context, colorRes))
    }

    fun setMsgTextColor(@ColorInt colorRes: Int): CommonDialog {
        mTvMsg.setTextColor(colorRes)
        return this
    }

    fun setLeftBtTextColorRes(@ColorRes colorRes: Int): CommonDialog {
        return setLeftBtTextColor(ContextCompat.getColor(context, colorRes))
    }

    fun setLeftBtTextColor(@ColorInt colorRes: Int): CommonDialog {
        mTvLeftBt.setTextColor(colorRes)
        return this
    }

    fun setRightBtTextColorRes(@ColorRes colorRes: Int): CommonDialog {
        return setRightBtTextColor(ContextCompat.getColor(context, colorRes))
    }

    fun setRightBtTextColor(@ColorInt colorRes: Int): CommonDialog {
        mTvRightBt.setTextColor(colorRes)
        return this
    }

    fun setAutoDimiss(autoDimiss: Boolean): CommonDialog {
        mIsAutoDismiss = autoDimiss
        return this
    }

    override fun onClick(view: View) {
        if (mIsAutoDismiss) dismiss()
        val id = view.id
        if (id == R.id.tv_left) {
            if (mListener != null) mListener!!.onLeftClick()
        } else if (id == R.id.tv_right) {
            if (mListener != null) mListener!!.onRightClick()
        }
    }

    abstract class Listener {
        abstract fun onRightClick()

        fun onLeftClick() {}
    }

    companion object {

        fun build(context: Context, msg: String): CommonDialog {
            val commonDialog = CommonDialog(context)
            commonDialog.setMsg(msg)
            return commonDialog
        }
    }

}















