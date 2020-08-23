package com.zhoujiulong.widgetlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhoujiulong.baselib.http.other.RequestErrorType;
import com.zhoujiulong.widgetlib.R;

/**
 * @author zhoujiulong
 * @createtime 2019/2/26 13:58
 * LoadingView
 */
public class LoadingView extends FrameLayout {

    private Context mContext;
    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mIntentErrorView;
    private TextView mIntentErrorBottomTv;
    private ImageView mIvErrorIcon;
    private TextView mTvErrorCenterMsg;
    private TextView mTvErrorBottomMsg;
    private View mEmptyView;
    private ImageView mIvEmptyIcon;
    private TextView mTvEmptyCenterMsg;
    private TextView mTvEmptyBottomMsg;
    private ImageView mViewLoading;

    //0：刷新，1：显示内容，2：网络错误，3：空页面，4：其它错误
    private int mInitType = 0;
    private @DrawableRes
    int mBackgroundRes = -1;
    private @DrawableRes
    int mErrorIconRes = -1;
    private String mErrorCenterMsgStr;
    private String mErrorBottomMsgStr;
    private @DrawableRes
    int mEmptyIconRes = -1;
    private String mEmptyCenterMsgStr;
    private String mEmptyBottomMsgStr;
    private AnimationDrawable mLoadingAni;
    private int mTopPadding = 0;
    private int mBottomPadding = 0;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WidgetLoadingView);
            mInitType = ta.getInt(R.styleable.WidgetLoadingView_widget_loading_init_type, 0);
            mBackgroundRes = ta.getResourceId(R.styleable.WidgetLoadingView_widget_loading_background, -1);
            mErrorIconRes = ta.getResourceId(R.styleable.WidgetLoadingView_widget_loading_error_icon, -1);
            mErrorCenterMsgStr = ta.getString(R.styleable.WidgetLoadingView_widget_loading_error_center_msg);
            mErrorBottomMsgStr = ta.getString(R.styleable.WidgetLoadingView_widget_loading_error_bottom_bt_msg);
            mEmptyIconRes = ta.getResourceId(R.styleable.WidgetLoadingView_widget_loading_empty_icon, -1);
            mEmptyCenterMsgStr = ta.getString(R.styleable.WidgetLoadingView_widget_loading_empty_center_msg);
            mEmptyBottomMsgStr = ta.getString(R.styleable.WidgetLoadingView_widget_loading_empty_bottom_bt_msg);
            mTopPadding = ta.getDimensionPixelOffset(R.styleable.WidgetLoadingView_widget_loading_top_padding, 0);
            mBottomPadding = ta.getDimensionPixelOffset(R.styleable.WidgetLoadingView_widget_loading_bottom_padding, 0);
            ta.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mContentView = getChildAt(0);
        }
        mLoadingView = View.inflate(mContext, R.layout.widget_loadingview_loading, null);
        mErrorView = View.inflate(mContext, R.layout.widget_loadingview_error, null);
        mEmptyView = View.inflate(mContext, R.layout.widget_loadingview_empty, null);
        mIntentErrorView = View.inflate(mContext, R.layout.widget_loadingview_intent_error, null);
        mLoadingView.setPadding(0, mTopPadding, 0, mBottomPadding);
        mErrorView.setPadding(0, mTopPadding, 0, mBottomPadding);
        mEmptyView.setPadding(0, mTopPadding, 0, mBottomPadding);
        mIntentErrorView.setPadding(0, mTopPadding, 0, mBottomPadding);
        addView(mLoadingView);
        addView(mErrorView);
        addView(mEmptyView);
        addView(mIntentErrorView);

        if (mBackgroundRes != -1) {
            mLoadingView.setBackgroundResource(mBackgroundRes);
            mErrorView.setBackgroundResource(mBackgroundRes);
            mEmptyView.setBackgroundResource(mBackgroundRes);
            mIntentErrorView.setBackgroundResource(mBackgroundRes);
        }

        mViewLoading = mLoadingView.findViewById(R.id.view_loading);
        mViewLoading.setImageResource(R.drawable.base_anim_loading);
        mLoadingAni = (AnimationDrawable) mViewLoading.getDrawable();

        mIvErrorIcon = mErrorView.findViewById(R.id.iv_error);
        mTvErrorCenterMsg = mErrorView.findViewById(R.id.tv_error_center);
        mTvErrorBottomMsg = mErrorView.findViewById(R.id.tv_error_bottom);

        mIvEmptyIcon = mEmptyView.findViewById(R.id.iv_empty);
        mTvEmptyCenterMsg = mEmptyView.findViewById(R.id.tv_empty_center);
        mTvEmptyBottomMsg = mEmptyView.findViewById(R.id.tv_empty_bottom);

        mIntentErrorBottomTv = mIntentErrorView.findViewById(R.id.tv_intent_error_bottom);

        setViewData();

        //0：刷新，1：显示内容，2：网络错误，3：空页面，4：其它错误
        switch (mInitType) {
            case 0:
                showLoading();
                break;
            case 1:
                showContent();
                break;
            case 2:
                showIntentError();
                break;
            case 3:
                showEmpty();
                break;
            case 4:
                showError();
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mLoadingAni != null) mLoadingAni.stop();
        super.onDetachedFromWindow();
    }

    private void setViewData() {
        if (mErrorIconRes != -1) mIvErrorIcon.setImageResource(mErrorIconRes);
        if (!TextUtils.isEmpty(mErrorCenterMsgStr)) mTvErrorCenterMsg.setText(mErrorCenterMsgStr);
        if (!TextUtils.isEmpty(mErrorBottomMsgStr)) mTvErrorBottomMsg.setText(mErrorBottomMsgStr);
        if (mEmptyIconRes != -1) mIvEmptyIcon.setImageResource(mEmptyIconRes);
        if (!TextUtils.isEmpty(mEmptyCenterMsgStr)) mTvEmptyCenterMsg.setText(mEmptyCenterMsgStr);
        if (!TextUtils.isEmpty(mEmptyBottomMsgStr)) mTvEmptyBottomMsg.setText(mEmptyBottomMsgStr);
    }

    /**
     * 同时设置空页面、网络错误和错误页面底部按钮点击事件回调
     */
    public void setOnBottomTvListener(OnClickListener listener) {
        setOnErrorListener(listener);
        setOnEmptyListener(listener);
        setIntentErrorListener(listener);
    }

    /**
     * 设置页面错误和网络错误地步按钮点击事件回调
     */
    public void setOnErrorAndIntentErrorListener(OnClickListener listener) {
        setOnErrorListener(listener);
        setIntentErrorListener(listener);
    }

    /**
     * 设置错误页面底部按钮点击事件回调
     */
    public void setOnErrorListener(OnClickListener listener) {
        mTvErrorBottomMsg.setOnClickListener(listener);
        if (mTvErrorBottomMsg.getVisibility() != VISIBLE) mTvErrorBottomMsg.setVisibility(VISIBLE);
    }

    /**
     * 设置空白页面底部按钮点击事件回调
     */
    public void setOnEmptyListener(OnClickListener listener) {
        mTvEmptyBottomMsg.setOnClickListener(listener);
        if (mTvEmptyBottomMsg.getVisibility() != VISIBLE) mTvEmptyBottomMsg.setVisibility(VISIBLE);
    }

    /**
     * 设置网络错误页面地步按钮点击事件回调
     */
    public void setIntentErrorListener(OnClickListener listener) {
        mIntentErrorBottomTv.setOnClickListener(listener);
        if (mIntentErrorBottomTv.getVisibility() != VISIBLE)
            mIntentErrorBottomTv.setVisibility(VISIBLE);
    }

    public void showContent() {
        hideAllView();
        if (mContentView != null) {
            mContentView.setVisibility(View.VISIBLE);
        }
    }

    public void showLoading() {
        hideAllView();
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingAni.start();
    }

    public void showEmpty() {
        hideAllView();
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void showError(RequestErrorType type) {
        switch (type) {
            case NO_INTERNET:
                showIntentError();
                break;
            default:
                showError();
                break;
        }
    }

    public void showError() {
        hideAllView();
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showIntentError() {
        hideAllView();
        mIntentErrorView.setVisibility(View.VISIBLE);
    }

    public void setEmptyCenterMsgStr(String msg) {
        if (TextUtils.isEmpty(msg)) return;
        this.mEmptyCenterMsgStr = msg;
        mTvEmptyCenterMsg.setText(msg);
    }

    private void hideAllView() {
        if (mLoadingAni != null) mLoadingAni.stop();
        if (mContentView != null && mContentView.getVisibility() == View.VISIBLE) {
            mContentView.setVisibility(View.GONE);
        }
        if (mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (mErrorView != null && mErrorView.getVisibility() == View.VISIBLE) {
            mErrorView.setVisibility(View.GONE);
        }
        if (mEmptyView != null && mEmptyView.getVisibility() == View.VISIBLE) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mIntentErrorView != null && mIntentErrorView.getVisibility() == View.VISIBLE) {
            mIntentErrorView.setVisibility(View.GONE);
        }
    }

}





















