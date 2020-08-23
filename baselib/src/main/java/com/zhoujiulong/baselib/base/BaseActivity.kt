package com.zhoujiulong.baselib.base

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<T : BaseViewModel<*>> : SimpleActivity() {

    protected val mViewModel: T by lazy {
        val viewModel = ViewModelProvider(this).get(getViewModelClass())
        viewModel.mShowLoadingData.observe(this, Observer {
            if (it) showLoading() else hideLoading()
        })
        viewModel
    }

    /**
     * 获取 ViewModel class
     */
    abstract fun getViewModelClass(): Class<T>

}









