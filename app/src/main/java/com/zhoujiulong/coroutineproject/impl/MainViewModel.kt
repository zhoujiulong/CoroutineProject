package com.zhoujiulong.coroutineproject.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zhoujiulong.baselib.base.BaseViewModel
import com.zhoujiulong.baselib.http.listener.DownLoadListener
import com.zhoujiulong.baselib.http.listener.RequestListener
import com.zhoujiulong.baselib.http.other.RequestErrorType
import com.zhoujiulong.baselib.http.response.DataResponse
import com.zhoujiulong.baselib.utils.ContextUtil
import com.zhoujiulong.baselib.utils.ToastUtil


/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:34
 * 空类，占位用
 */
class MainViewModel : BaseViewModel<MainModel>() {

    val mDownLoadSuccess by lazy { MutableLiveData<String>() }
    val mDownLoadFail by lazy { MutableLiveData<String>() }

    override fun initModel() = MainModel()

    fun downLoadApk() {
        val fileDir = ContextUtil.getContext().cacheDir.absolutePath
        val fileName = "downLoad2.apk"
        showLoading()
        mModel.downLoadApk(object : DownLoadListener() {
            override fun onProgress(progress: Int) {
                Log.d("zhoujiulong", "下载进度：$progress")
            }

            override fun onDone(fileAbsPath: String) {
                hideLoading()
                mDownLoadSuccess.value = fileAbsPath
            }

            override fun onFail(errorInfo: String) {
                hideLoading()
                mDownLoadFail.value = errorInfo
            }
        }, fileDir, fileName)
    }

    fun requestTest() {
        showLoading()
        mModel.requestTest(object : RequestListener<DataResponse<String>>() {
            override fun requestSuccess(data: DataResponse<String>) {
                hideLoading()
                ToastUtil.toast("请求成功：${data.data}")
                Log.d("zhoujiulongtest", "requestSuccess.thread:${Thread.currentThread().name}")
            }

            override fun requestError(
                data: DataResponse<String>?, type: RequestErrorType, msg: String, code: Int
            ) {
                hideLoading()
                ToastUtil.toast("请求失败：$msg")
                Log.d("zhoujiulongtest", "requestError.thread:${Thread.currentThread().name}")
            }
        })
    }

}
























