package com.zhoujiulong.baselib.base

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<T : BaseViewModel<*>> : SimpleFragment() {

    protected val mViewModel: T by lazy {
        val viewModel = initViewModel(ViewModelProvider(this))
        viewModel.mShowLoadingData.observe(this, Observer {
            if (it) showLoading() else hideLoading()
        })
        viewModel
    }

    /**
     * 创建 ViewModel
     */
    abstract fun initViewModel(provider: ViewModelProvider): T

}
