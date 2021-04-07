package com.zhoujiulong.assembly_b

import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zhoujiulong.assembly_b.databinding.BActivityAssemblyBMainBinding
import com.zhoujiulong.baselib.base.SimpleActivity
import com.zhoujiulong.commonlib.constants.RouteGroupConstants
import com.zhoujiulong.commonlib.constants.RouteNameConstants

/**
 * @author zhoujiulong
 * @createtime 2019/9/6 15:00
 */
@Route(path = RouteNameConstants.ASSEMBLY_B_MAIN, group = RouteGroupConstants.ASSEMBLY_B)
class AssemblyBMainActivity : SimpleActivity<BActivityAssemblyBMainBinding>() {

    override fun getViewBinding() = BActivityAssemblyBMainBinding.inflate(LayoutInflater.from(this))

    override fun initView() {
    }

    override fun initListener() {
        setOnClick(mBinding.tvGoA)
    }

    override fun initData() {
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.tvGoA -> ARouter.getInstance().build(RouteNameConstants.ASSEMBLY_A_MAIN)
                .navigation()
        }
    }

}
