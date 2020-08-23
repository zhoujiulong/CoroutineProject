package com.zhoujiulong.baselib.utils

import android.os.Environment

import java.io.File
import java.io.IOException

/**
 * @author zhoujiulong
 * @createtime 2019/4/11 9:32
 */
object FileUtil {

    /**
     * @throws IOException 判断下载目录是否存在
     */
    @Throws(IOException::class)
    fun isExistDir(saveDir: String): String {
        // 下载位置
        val downloadFile = File(Environment.getExternalStorageDirectory(), saveDir)
        if (!downloadFile.mkdirs()) {
            val result = downloadFile.createNewFile()
        }
        return downloadFile.absolutePath
    }

    /**
     * @return 从下载连接中解析出文件名
     */
    fun getNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

}
















