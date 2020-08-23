package com.zhoujiulong.baselib.http.other

import com.zhoujiulong.baselib.http.listener.UploadListener
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.*
import java.io.File
import java.io.IOException

/**
 * @author zhoujiulong
 * @createtime 2019/2/26 16:49
 * 上传文件监听上传进度
 */
class UploadRequestBody private constructor(
    private val delegate: RequestBody,
    private val listener: UploadListener,
    private val mFilePath: String
) : RequestBody() {

    companion object {

        fun create(listener: UploadListener, uploadFile: File): UploadRequestBody {
            //application/octet-stream: 任意二进制数据流
            val requestBodyOuter =
                uploadFile.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            return UploadRequestBody(requestBodyOuter, listener, uploadFile.absolutePath)
        }
    }

    override fun contentType(): MediaType? {
        return delegate.contentType()
    }

    override fun contentLength(): Long {
        try {
            return delegate.contentLength()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return -1
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val bufferedSink: BufferedSink

        val countingSink = CountingSink(sink)
        bufferedSink = countingSink.buffer()

        delegate.writeTo(bufferedSink)

        bufferedSink.flush()
    }

    private inner class CountingSink internal constructor(delegate: Sink) :
        ForwardingSink(delegate) {

        private var bytesWritten: Long = 0

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)

            bytesWritten += byteCount
            val progress =
                if (contentLength() > 0) (bytesWritten * 100 / contentLength()).toInt() else 0
            listener.onUploadProgress(progress, bytesWritten, contentLength(), mFilePath)
        }
    }

}







