package com.zhoujiulong.coroutineproject.impl

import com.zhoujiulong.baselib.base.BaseModel
import com.zhoujiulong.baselib.http.HttpUtil
import com.zhoujiulong.baselib.http.listener.DownLoadListener
import com.zhoujiulong.baselib.http.listener.RequestListener
import com.zhoujiulong.baselib.http.other.TimeOut
import com.zhoujiulong.baselib.http.response.DataResponse

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:32
 * 空类，占位用
 */
class MainModel : BaseModel<MainService>() {

    override fun initService(): MainService {
        return HttpUtil.getService(
            MainService::class.java, TimeOut(TimeOut.TimeOutType.READ_TIMEOUT, 200L)
        )
    }

    fun downLoadApk(downloadListener: DownLoadListener, filePath: String, fileName: String) {
        HttpUtil.sendDownloadRequest(
            mScope,
            mService.downLoadApk("http://imtt.dd.qq.com/16891/9FFDE35ADEFC28D3740D4E16612F078A.apk?fsname=com.tencent.tmgp.sgame_1.22.1.13_22011304.apk&csr=1bbd"),
            filePath, fileName, downloadListener
        )
    }

    fun requestTest(listener: RequestListener<DataResponse<String>>) {
        HttpUtil.sendRequest(mScope, mService.test("abc"), listener)
    }
}
























