package com.zhoujiulong.baselib.image.bean

/**
 * Created by shiming on 2016/10/26.
 */

class ImageSize(val width: Int, val height: Int) {

    override fun toString(): String {
        return StringBuilder().append(width).append(SEPARATOR).append(height).toString()
    }

    companion object {
        private val SEPARATOR = "x"
    }
}
