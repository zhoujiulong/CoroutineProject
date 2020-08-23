package com.zhoujiulong.assembly_a

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zhoujiulong.baselib.base.SimpleActivity
import com.zhoujiulong.baselib.image.ImageLoader
import com.zhoujiulong.commonlib.constants.RouteGroupConstants
import com.zhoujiulong.commonlib.constants.RouteNameConstants
import kotlinx.android.synthetic.main.a_activity_assembly_a_main.*

/**
 * @author zhoujiulong
 * @createtime 2019/9/6 14:58
 */
@Route(path = RouteNameConstants.ASSEMBLY_A_MAIN, group = RouteGroupConstants.ASSEMBLY_A)
class AssemblyAMainActivity : SimpleActivity() {

    override fun getLayoutId(): Int = R.layout.a_activity_assembly_a_main

    override fun initView() {
    }

    override fun initListener() {
        setOnClick(tv_go_b, btDownPic)
    }

    override fun initData() {
    }

    override fun getData() {
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_go_b -> ARouter.getInstance().build(RouteNameConstants.ASSEMBLY_B_MAIN).navigation()
            btDownPic -> {
                val url =
                    "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1946151466,3034958414&fm=26&gp=0.jpg"
                ImageLoader.getInstance()
                    .displayImage(this, url, iv, R.mipmap.ic_pic_holder, R.mipmap.ic_pic_holder)
            }
        }
    }

}
