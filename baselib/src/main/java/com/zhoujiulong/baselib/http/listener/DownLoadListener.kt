package com.zhoujiulong.baselib.http.listener


/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : 下载监听
 */
abstract class DownLoadListener {

    /**
     * 下载进度
     */
    open fun onProgress(progress: Int) {}

    /**
     * 下载完成的文件
     */
    abstract fun onDone(fileAbsPath: String)

    /**
     * 下载失败
     */
    abstract fun onFail(errorInfo: String)

}

















