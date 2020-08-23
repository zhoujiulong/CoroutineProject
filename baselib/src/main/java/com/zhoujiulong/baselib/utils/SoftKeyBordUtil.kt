package com.zhoujiulong.baselib.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object SoftKeyBordUtil {

    /**
     * 打开软键盘
     */
    fun openKeybord(mEditText: EditText, activity: Activity) {
        if (isSoftInputShow(activity)) return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
    }

    /**
     * 关闭软键盘
     */
    fun closeKeybord(view: View, activity: Activity) {
        if (!isSoftInputShow(activity)) return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 判断当前软键盘是否打开
     */
    fun isSoftInputShow(activity: Activity): Boolean {
        // 虚拟键盘隐藏 判断view是否为空
        val view = activity.window.peekDecorView()
        if (view != null) {
            // 隐藏虚拟键盘
            val inputmanger =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputmanger != null) {
                return inputmanger.isActive && activity.window.currentFocus != null
            }
        }
        return false
    }

}
