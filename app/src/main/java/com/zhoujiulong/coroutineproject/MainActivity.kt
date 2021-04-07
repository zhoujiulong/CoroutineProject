package com.zhoujiulong.coroutineproject

import android.Manifest
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.zhoujiulong.baselib.base.BaseActivity
import com.zhoujiulong.baselib.utils.JsonUtil
import com.zhoujiulong.baselib.utils.ToastUtil
import com.zhoujiulong.commonlib.constants.RouteNameConstants
import com.zhoujiulong.coroutineproject.databinding.ActivityMainBinding
import com.zhoujiulong.coroutineproject.impl.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(LayoutInflater.from(this))

    override fun getViewModelClass(): Class<MainViewModel> {
        mIsSaveStateViewModel = true
        return MainViewModel::class.java
    }

    override fun initView() {
    }

    override fun initListener() {
        setOnClick(
            mBinding.tvGoA, mBinding.tvGoB, mBinding.tvPermissionTest,
            mBinding.tvRequestTest, mBinding.tvDownloadFile
        )

        mViewModel.mDownLoadProgress.observe(this, Observer {
            mBinding.tvDownloadDetail.text = "下载进度：$it"
        })
        mViewModel.mDownLoadSuccess.observe(this, Observer {
            ToastUtil.toast("下载成功：$it")
        })
        mViewModel.mDownLoadFail.observe(this, Observer {
            ToastUtil.toast("下载失败：$it")
        })
    }

    override fun initData() {
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.tvGoA -> ARouter.getInstance().build(RouteNameConstants.ASSEMBLY_A_MAIN)
                .navigation()
            mBinding.tvGoB -> ARouter.getInstance().build(RouteNameConstants.ASSEMBLY_B_MAIN)
                .navigation()
            mBinding.tvPermissionTest -> requestPermissionTest()
            mBinding.tvRequestTest -> mViewModel.requestTest()
            mBinding.tvDownloadFile -> mViewModel.downLoadApk()
        }
    }

    private fun requestPermissionTest() {
        requestPermission(
            {
                ToastUtil.toast("获取权限成功")
            }, {
                ToastUtil.toast("获取权限失败:${JsonUtil.object2String(it)}")
            },
            Manifest.permission.CAMERA,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE
        )
    }

}























