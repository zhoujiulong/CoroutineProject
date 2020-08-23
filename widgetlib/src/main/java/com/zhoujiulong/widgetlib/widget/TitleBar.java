package com.zhoujiulong.widgetlib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.zhoujiulong.baselib.utils.ScreenUtil;
import com.zhoujiulong.baselib.utils.StatusBarUtil;
import com.zhoujiulong.baselib.utils.ToastUtil;
import com.zhoujiulong.widgetlib.R;

/**
 * @author zhoujiulong
 * @createtime 2019/2/26 10:44
 * 标题栏
 */
public class TitleBar extends Toolbar implements View.OnClickListener {

    private View mTopEmptyView;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private String mTitleStr;
    private TextView mTvRightTitle;
    private String mRightTitleStr;
    private int mTitleBackgroundColor;
    private ImageView mIvRightIcon;
    private @DrawableRes
    int mIvRightIconRes;
    private boolean mIsShowBackIcon;
    //状态栏是否透明
    private boolean mNeedSetStatuBarTransparent = true;

    private OnClickListener mBackListener;
    private OnClickListener mRightTitleListener;
    private OnClickListener mRightIconListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, androidx.appcompat.R.attr.toolbarStyle);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initView(context);
        if (mNeedSetStatuBarTransparent) initStatusBar();
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WidgetTitleBar);
            mTitleStr = ta.getString(R.styleable.WidgetTitleBar_widget_title_text);
            mRightTitleStr = ta.getString(R.styleable.WidgetTitleBar_widget_title_right_text);
            mTitleBackgroundColor = ta.getColor(R.styleable.WidgetTitleBar_widget_title_background, -1);
            mIsShowBackIcon = ta.getBoolean(R.styleable.WidgetTitleBar_widget_title_show_backicon, true);
            mIvRightIconRes = ta.getResourceId(R.styleable.WidgetTitleBar_widget_title_right_icon, -1);
            ta.recycle();
        }

        if (mTitleBackgroundColor != -1) {
            setBackgroundColor(mTitleBackgroundColor);
        }
        setTitleTextAppearance(context, R.style.WidgetTitleText);
        setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setContentInsetsRelative(0, 0);
    }

    private void initView(final Context context) {
        View titleView = View.inflate(context, R.layout.widget_titlebar, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(titleView, params);
        mTopEmptyView = findViewById(R.id.top_empty_view);
        mTvTitle = findViewById(R.id.tv_title);
        if (mTitleStr != null) mTvTitle.setText(mTitleStr);

        mTvRightTitle = findViewById(R.id.tv_right);
        if (mRightTitleStr != null) {
            mTvRightTitle.setVisibility(VISIBLE);
            mTvRightTitle.setText(mRightTitleStr);
        }
        mTvRightTitle.setOnClickListener(this);

        mIvBack = titleView.findViewById(R.id.iv_back);
        mIvBack.setVisibility(mIsShowBackIcon ? VISIBLE : GONE);
        mIvBack.setOnClickListener(this);

        mIvRightIcon = titleView.findViewById(R.id.iv_right_icon);
        mIvRightIcon.setOnClickListener(this);
        if (mIvRightIconRes != -1) {
            mIvRightIcon.setVisibility(VISIBLE);
            mIvRightIcon.setImageResource(mIvRightIconRes);
        }
    }

    private void initStatusBar() {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            if (StatusBarUtil.INSTANCE.translucent(activity)) {
                StatusBarUtil.INSTANCE.setStatusBarLightMode(activity);
                ViewGroup.LayoutParams params = mTopEmptyView.getLayoutParams();
                params.height = ScreenUtil.INSTANCE.getStatusBarHeight(activity);
                mTopEmptyView.setLayoutParams(params);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            if (mBackListener != null) {
                mBackListener.onClick(view);
            } else if (getContext() instanceof Activity) {
                Activity activity = (Activity) getContext();
                activity.finish();
            } else {
                ToastUtil.INSTANCE.toast("TitleBar: context 不是 Activity");
            }
        } else if (id == R.id.tv_right) {
            if (mRightTitleListener != null) {
                mRightTitleListener.onClick(view);
            }
        } else if (id == R.id.iv_right_icon) {
            if (mRightIconListener != null) {
                mRightIconListener.onClick(view);
            }
        }
    }

    /**
     * 设置拦截返回按钮点击事件监听，自己处理
     */
    public void setOnBackListener(OnClickListener backListener) {
        mBackListener = backListener;
    }

    /**
     * 设置右侧标题点击时间监听
     */
    public void setOnRightTitleListener(OnClickListener rightTitleListener) {
        mRightTitleListener = rightTitleListener;
    }

    /**
     * 设置标题
     */
    public void setTitleText(String title) {
        mTitleStr = title;
        mTvTitle.setText(title);
    }

    /**
     * 设置副标题
     */
    public void setRightTitleText(String rightStr) {
        mRightTitleStr = rightStr;
        mTvRightTitle.setVisibility(VISIBLE);
        mTvRightTitle.setText(rightStr);
    }

    /**
     * 设置标题右侧图标点击事件
     */
    public void setRightIconListener(OnClickListener rightIconListener) {
        this.mRightIconListener = rightIconListener;
    }
}






















