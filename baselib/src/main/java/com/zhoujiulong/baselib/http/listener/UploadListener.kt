package com.zhoujiulong.baselib.http.listener

/**
 * @author zhoujiulong
 * @createtime 2019/2/26 16:54
 * 上传文件回调
 */
interface UploadListener {

    /**
     * 上传进度回调
     *
     * @param progress      上传进度
     * @param bytesWritten  已上传
     * @param contentLength 总大小
     * @param fileName      上传文件的文件名，如果同时上传多个文件可以用这个做区分
     */
    fun onUploadProgress(progress: Int, bytesWritten: Long, contentLength: Long, fileName: String)

}
