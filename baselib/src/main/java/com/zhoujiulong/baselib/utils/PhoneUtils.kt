package com.zhoujiulong.baselib.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import android.text.TextUtils

/**
 * @author zhoujiulong
 * @createtime 2019/3/7 16:14
 */
object PhoneUtils {

    /**
     * 获取 App 版本号
     */
    val appVersion: String
        get() {
            var version = ""
            try {
                val manager = ContextUtil.getContext().packageManager
                val info = manager.getPackageInfo(ContextUtil.getContext().packageName, 0)
                version = info.versionName
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return version
        }

    /**
     * 获取版本号Code
     */
    val appCode: Int
        get() {
            val manager = ContextUtil.getContext().packageManager
            var code = 0
            try {
                val info = manager.getPackageInfo(ContextUtil.getContext().packageName, 0)
                code = info.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return code
        }

    /**
     * 获取设备 ID
     */
    val systemId: String
        get() = Settings.System.getString(
            ContextUtil.getContext().contentResolver,
            Settings.System.ANDROID_ID
        )

    /**
     * 是否使用代理(WiFi状态下的,避免被抓包)
     */
    val isWifiProxy: Boolean
        get() {
            return try {
                val proxyAddress: String? = System.getProperty("http.proxyHost")
                val portstr = System.getProperty("http.proxyPort")
                val proxyPort = Integer.parseInt(portstr ?: "-1")
                !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
            } catch (e: Exception) {
                false
            }
        }

    /**
     * 跳转到权限设置界面
     */
    fun goAppDetailSetting(context: Context) {
        PermissionSettingPage.start(context, false)
    }

    /**
     * 将文本复制到系统的剪切板
     */
    fun clipTextToSystem(context: Context, text: String) {
        //获取剪贴板管理器：
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("xingfugo", text)
        // 将ClipData内容放到系统剪贴板里。
        cm.primaryClip = mClipData
    }

    /**
     * 获取用户选择的联系人信息
     *
     * @return list【0】:name   list[1] :phone
     */
    fun getPhoneContacts(uri: Uri): Array<String>? {
        try {
            val contact = arrayOf("", "")
            //得到ContentResolver对象
            val cr = ContextUtil.getContext().contentResolver
            //取得电话本中开始一项的光标
            val cursor = cr.query(uri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val nameFieldColumnIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                if (nameFieldColumnIndex < 0) return null
                contact[0] = cursor.getString(nameFieldColumnIndex)
                val nameColumnIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (nameColumnIndex < 0) return contact
                var phoneNum = cursor.getString(nameColumnIndex)
                if (!TextUtils.isEmpty(phoneNum)) {
                    phoneNum = phoneNum.replace(" ", "")
                    contact[1] = phoneNum
                }
                cursor.close()
            } else {
                return null
            }
            return contact
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

}





















