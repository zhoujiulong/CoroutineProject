package com.zhoujiulong.baselib.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

abstract class BaseViewModel<M : BaseModel<*>> : ViewModel() {

    protected val mModel: M
    protected val Tag: String

    val mShowLoadingData by lazy { MutableLiveData<Boolean>() }

    init {
        mModel = this.initModel()
        mModel.attach(viewModelScope)
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
        mModel.onCleared()
    }

}
