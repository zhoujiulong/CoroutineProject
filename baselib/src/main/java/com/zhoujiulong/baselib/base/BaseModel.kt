package com.zhoujiulong.baselib.base

import kotlinx.coroutines.CoroutineScope

abstract class BaseModel<T> {

    protected val Tag: String
    protected val mService: T
    protected lateinit var mScope: CoroutineScope

    init {
        mService = this.initService()
        Tag = System.currentTimeMillis().toString()
    }

    abstract fun initService(): T

    fun attach(scope: CoroutineScope) {
        mScope = scope
    }

    fun onCleared() {
    }

}
