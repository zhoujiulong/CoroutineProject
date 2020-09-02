package com.zhoujiulong.baselib.base

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<T : BaseViewModel<*>> : SimpleActivity() {

    protected val mViewModel: T by lazy {
        val cl = getViewModelClass()
        val viewModel = if (mIsSaveStateViewModel) {
            SavedStateViewModelFactory(application, this).create(cl)
        } else {
            ViewModelProvider(this).get(cl)
        }
        viewModel.mShowLoadingData.observe(this, Observer {
            if (it) showLoading() else hideLoading()
        })

        viewModel
    }

    /**
     * 是否是加载 SavedStateViewModel
     */
    protected var mIsSaveStateViewModel = false

    /**
     * 获取 ViewModel class
     */
    abstract fun getViewModelClass(): Class<T>

}









