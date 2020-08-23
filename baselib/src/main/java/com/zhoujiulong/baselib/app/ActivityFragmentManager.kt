package com.zhoujiulong.baselib.app

import com.zhoujiulong.baselib.base.SimpleActivity
import com.zhoujiulong.baselib.base.SimpleFragment
import java.util.*

/**
 * 应用Activity管理类
 */
class ActivityFragmentManager private constructor() {

    companion object {

        @Volatile
        private var fragmentStack: Stack<SimpleFragment> = Stack()
        @Volatile
        private var activityStack: Stack<SimpleActivity> = Stack()
        @Volatile
        private var instance: ActivityFragmentManager? = null

        /**
         * 获得单例对象
         *
         * @return ActivityFragmentManager
         */
        fun getInstance(): ActivityFragmentManager {
            if (instance == null) {
                synchronized(ActivityFragmentManager::class.java) {
                    if (instance == null) instance =
                        ActivityFragmentManager()
                }
            }
            return instance!!
        }
    }

    /**
     * 获取当前的Fragment
     */
    val currentFragment: SimpleFragment?
        get() = if (fragmentStack.size > 0) fragmentStack.lastElement() else null

    /**
     * 添加 Fragment
     *
     * @param fragment Fragment
     */
    fun addFragment(fragment: SimpleFragment) {
        fragmentStack.add(fragment)
    }

    /**
     * 移除 Fragment
     *
     * @param fragment Fragment
     */
    fun removeFragment(fragment: SimpleFragment) {
        fragmentStack.remove(fragment)
    }

    /**
     * 获取当前的Activity
     */
    val currentActivity: SimpleActivity
        get() = activityStack.lastElement()

    /**
     * 添加 Activity
     *
     * @param activity Activity
     */
    fun addActivity(activity: SimpleActivity) {
        activityStack.add(activity)
    }

    /**
     * 移除 Activity
     *
     * @param activity Activity
     */
    fun removeActivity(activity: SimpleActivity) {
        activityStack.remove(activity)
    }

    /**
     * 结束指定Activity
     *
     * @param activity Activity
     */
    fun finishActivity(activity: SimpleActivity) {
        activityStack.remove(activity)
        activity.finish()
    }

    /**
     * 结束指定Activity
     *
     * @param cls Activity.class
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity != null && activity.javaClass == cls) {
                activityStack.remove(activity)
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun appExit() {
        try {
            finishAllActivity()
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
