package com.zhoujiulong.baselib.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.zhoujiulong.baselib.BuildConfig
import com.zhoujiulong.baselib.utils.ContextUtil

/**
 * @author zhoujiulong
 * @createtime 2019/7/11 13:34
 */
open class SimpleApplication : Application() {

    companion object {
        lateinit var instance: Application
            private set
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ContextUtil.init(this)
        initARouter()
    }

    /**
     * 初始化 ARouter
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

}

















