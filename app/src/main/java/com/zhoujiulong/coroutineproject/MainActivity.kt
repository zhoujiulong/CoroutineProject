package com.zhoujiulong.coroutineproject

import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.zhoujiulong.baselib.base.BaseActivity
import com.zhoujiulong.baselib.utils.ToastUtil
import com.zhoujiulong.commonlib.constants.RouteNameConstants
import com.zhoujiulong.coroutineproject.impl.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class.java

    override fun initView() {
    }

    override fun initListener() {
        setOnClick(tv_go_a, tv_go_b, tvRequestTest, tv_download_file)

        mViewModel.mDownLoadProgress.observe(this, Observer {
            tv_download_detail.text = "下载进度：$it"
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
            tv_go_a -> ARouter.getInstance().build(RouteNameConstants.ASSEMBLY_A_MAIN).navigation()
            tv_go_b -> ARouter.getInstance().build(RouteNameConstants.ASSEMBLY_B_MAIN).navigation()
            tvRequestTest -> mViewModel.requestTest()
            tv_download_file -> mViewModel.downLoadApk()
        }
    }

}























