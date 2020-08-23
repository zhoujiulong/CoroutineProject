package com.zhoujiulong.baselib.image.tranform


import android.graphics.Bitmap

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

import java.security.MessageDigest

/**
 * author： Created by shiming on 2018/4/19 18:23
 * mailbox：lamshiming@sina.com
 * 高斯模糊
 */

class BlurBitmapTranformation(private var radius: Int) : BitmapTransformation() {

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap? {
        return blurCrop(pool, toTransform)
    }

    private fun blurCrop(pool: BitmapPool, toTransform: Bitmap?): Bitmap? {

        if (toTransform == null) {
            return null
        }
        if (radius <= 0) {
            radius = 25
        }
        val target: Bitmap
        //前面使用的资源，后面使用的是pool，
        target = blur(toTransform, radius, pool)

        return target
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    private fun blur(source: Bitmap, radius: Int, pool: BitmapPool): Bitmap {
        //        int w = source.getWidth();
        //        int h = source.getHeight();
        //        //由于有这个对象，可以这样的获取尺寸，方便对图片的操作，和对垃圾的回收
        //        Bitmap target = pool.get(w, h, Bitmap.Config.ARGB_8888);
        //        //copy
        //        Bitmap bitmap = target.copy(source.getConfig(), true);
        val bitmap = source.copy(source.config, true)
        val w = source.width
        val h = source.height
        val pix = IntArray(w * h)
        //        像素颜色写入位图
        //        要跳过的像素[ [] ]中的条目数
        //*行（必须是=位图的宽度）。
        //        从每行读取的像素数
        //        要读取的行数。
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)

        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1

        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(Math.max(w, h))

        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }

        yi = 0
        yw = yi

        val stack = Array(div) { IntArray(3) }
        var stackPointer: Int
        var stackStart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routSum: Int
        var goutSum: Int
        var boutSum: Int
        var rinSum: Int
        var ginSum: Int
        var binSum: Int

        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutSum = rsum
            goutSum = boutSum
            routSum = goutSum
            binSum = routSum
            ginSum = binSum
            rinSum = ginSum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                //    12&5 的值是多少？答：12转成二进制数是1100（前四位省略了），
                // 5转成二进制数是0101，则运算后的结果为0100即4  这是两侧为数值时；
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinSum += sir[0]
                    ginSum += sir[1]
                    binSum += sir[2]
                } else {
                    routSum += sir[0]
                    goutSum += sir[1]
                    boutSum += sir[2]
                }
                i++
            }
            stackPointer = radius

            x = 0
            while (x < w) {

                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]

                rsum -= routSum
                gsum -= goutSum
                bsum -= boutSum

                stackStart = stackPointer - radius + div
                sir = stack[stackStart % div]

                routSum -= sir[0]
                goutSum -= sir[1]
                boutSum -= sir[2]

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]

                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff

                rinSum += sir[0]
                ginSum += sir[1]
                binSum += sir[2]

                rsum += rinSum
                gsum += ginSum
                bsum += binSum

                stackPointer = (stackPointer + 1) % div
                sir = stack[stackPointer % div]

                routSum += sir[0]
                goutSum += sir[1]
                boutSum += sir[2]

                rinSum -= sir[0]
                ginSum -= sir[1]
                binSum -= sir[2]

                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutSum = rsum
            goutSum = boutSum
            routSum = goutSum
            binSum = routSum
            ginSum = binSum
            rinSum = ginSum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x

                sir = stack[i + radius]

                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]

                rbs = r1 - Math.abs(i)

                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs

                if (i > 0) {
                    rinSum += sir[0]
                    ginSum += sir[1]
                    binSum += sir[2]
                } else {
                    routSum += sir[0]
                    goutSum += sir[1]
                    boutSum += sir[2]
                }

                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackPointer = radius
            y = 0
            while (y < h) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                //在该位置点出现一个萌白的点
                pix[yi] =
                    -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]

                rsum -= routSum
                gsum -= goutSum
                bsum -= boutSum

                stackStart = stackPointer - radius + div
                sir = stack[stackStart % div]

                routSum -= sir[0]
                goutSum -= sir[1]
                boutSum -= sir[2]

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w
                }
                p = x + vmin[y]

                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]

                rinSum += sir[0]
                ginSum += sir[1]
                binSum += sir[2]

                rsum += rinSum
                gsum += ginSum
                bsum += binSum

                stackPointer = (stackPointer + 1) % div
                sir = stack[stackPointer]

                routSum += sir[0]
                goutSum += sir[1]
                boutSum += sir[2]
                rinSum -= sir[0]
                ginSum -= sir[1]
                binSum -= sir[2]
                yi += w
                y++
            }
            x++
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h)
        return bitmap
    }
}
