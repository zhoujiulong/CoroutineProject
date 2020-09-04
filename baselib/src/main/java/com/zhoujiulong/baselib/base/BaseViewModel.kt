package com.zhoujiulong.baselib.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

/**
 * ViewModel 基类
 */
abstract class BaseViewModel<M : BaseRepository<*>> : ViewModel() {

    protected val mRepository: M
    protected val Tag: String

    val mShowLoadingData by lazy { MutableLiveData<Boolean>() }

    init {
        mRepository = this.initModel()
        mRepository.attach(viewModelScope)
        Tag = System.currentTimeMillis().toString()
    }

    abstract fun initModel(): M

    protected fun showLoading() {
        mShowLoadingData.postValue(true)
    }

    protected fun hideLoading() {
        mShowLoadingData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.onCleared()
    }

}
