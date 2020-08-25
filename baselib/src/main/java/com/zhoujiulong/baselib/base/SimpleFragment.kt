package com.zhoujiulong.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zhoujiulong.baselib.app.ActivityFragmentManager

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : SimpleFragment
 */
abstract class SimpleFragment : Fragment(), View.OnClickListener {

    private var mISFirstResume = true

    protected lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(getLayoutId(), container, false)
        ActivityFragmentManager.getInstance().addFragment(this)

        return mRootView
    }

    override fun onResume() {
        super.onResume()
        if (mISFirstResume) {
            mISFirstResume = false

            initView()
            initListener()
            initData()
            getData()
        }
    }

    override fun onDestroyView() {
        ActivityFragmentManager.getInstance().removeFragment(this)
        super.onDestroyView()
    }

    fun showLoading() {
        if (activity is SimpleActivity) (activity as SimpleActivity).showLoading()
    }

    fun hideLoading() {
        if (activity is SimpleActivity) (activity as SimpleActivity).hideLoading()
    }

    /* ********************************************** 请求权限 **************************************************** */
    /* ********************************************** 请求权限 **************************************************** */

    /**
     * 请求权限
     */
    fun requestPermission(
        success: () -> Unit,
        fail: (unGrantPermissions: List<String>) -> Unit,
        vararg permissions: String
    ) {
        if (activity is SimpleActivity) {
            (activity as SimpleActivity).requestPermission(success, fail, *permissions)
        }
    }

    /* ********************************************** 初始化相关方法 **************************************************** */
    /* ********************************************** 初始化相关方法 **************************************************** */

    /**
     * 获取布局资源 id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化控件
     */
    protected abstract fun initView()

    /**
     * 初始化监听事件
     */
    protected abstract fun initListener()

    /**
     * 初始化数据,设置数据
     */
    protected abstract fun initData()

    /**
     * 获取网络数据
     */
    protected abstract fun getData()

    /**
     * 設置點擊
     */
    fun setOnClick(vararg views: View) {
        for (view in views) view.setOnClickListener(this)
    }

}


















