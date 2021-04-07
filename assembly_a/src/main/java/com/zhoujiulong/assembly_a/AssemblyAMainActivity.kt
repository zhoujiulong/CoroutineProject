package com.zhoujiulong.assembly_a

import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zhoujiulong.assembly_a.databinding.AActivityAssemblyAMainBinding
import com.zhoujiulong.baselib.base.SimpleActivity
import com.zhoujiulong.baselib.image.ImageLoader
import com.zhoujiulong.commonlib.constants.RouteGroupConstants
import com.zhoujiulong.commonlib.constants.RouteNameConstants

/**
 * @author zhoujiulong
 * @createtime 2019/9/6 14:58
 */
@Route(path = RouteNameConstants.ASSEMBLY_A_MAIN, group = RouteGroupConstants.ASSEMBLY_A)
class AssemblyAMainActivity : SimpleActivity<AActivityAssemblyAMainBinding>() {

    override fun getViewBinding() = AActivityAssemblyAMainBinding.inflate(LayoutInflater.from(this))

    override fun initView() {

    }

    override fun initListener() {
        setOnClick(mBinding.tvGoB, mBinding.btDownPic)
    }

    override fun initData() {
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.tvGoB -> ARouter.getInstance().build(RouteNameConstants.ASSEMBLY_B_MAIN)
                .navigation()
            mBinding.btDownPic -> {
                val url =
                    "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1946151466,3034958414&fm=26&gp=0.jpg"
                ImageLoader.getInstance()
                    .displayImage(this, url, mBinding.iv, R.mipmap.ic_pic_holder, R.mipmap.ic_pic_holder)
            }
        }
    }

}
