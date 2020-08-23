package com.zhoujiulong.widgetlib.loadmore_refresh_view

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhoujiulong.widgetlib.R

/**
 * @author zhoujiulong
 * @createtime 2019/3/8 13:39
 */
class NormalLoadMoreView : BaseLoadMoreView() {

    override fun getRootView(parent: ViewGroup) =
        parent.getItemView(R.layout.widget_loadmore_normalview)

    override fun getLoadingView(holder: BaseViewHolder) =
        holder.getView<View>(R.id.load_more_loading_view)

    override fun getLoadComplete(holder: BaseViewHolder) =
        holder.getView<View>(R.id.load_more_load_complete)

    override fun getLoadFailView(holder: BaseViewHolder) =
        holder.getView<View>(R.id.load_more_load_fail_view)

    override fun getLoadEndView(holder: BaseViewHolder) =
        holder.getView<View>(R.id.load_more_load_end_view)

}



















