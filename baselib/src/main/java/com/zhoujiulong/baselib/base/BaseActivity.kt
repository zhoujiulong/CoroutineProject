package com.zhoujiulong.baselib.base

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<T : BaseViewModel<out BaseRepository<*>>> : SimpleActivity() {

    protected val mViewModel: T by lazy {
        val cl = getViewModelClass()
        val viewModel = if (mIsSaveStateViewModel) {
            ViewModelProvider(this, SavedStateViewModelFactory(application, this)).get(cl)
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









