package com.zhoujiulong.widgetlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.zhoujiulong.widgetlib.R;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Day : 2018/12/24
 * 描述 : 自定义圆角 ImageView，
 * 圆角规则：如果定义了 allRadius 又定义了单个的圆角取单个的圆角，没单独设置的取 allRadius
 */
public class RoundImageView extends ImageView {

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {50, 50, 50, 50, 50, 50, 50, 50};//默认圆角半径为50：轮播图使用
    private Path mPath;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WidgetRoundImageView);
            int allRadius = ta.getDimensionPixelOffset(R.styleable.WidgetRoundImageView_widget_all_radius, 0);

            int topLeftRadius = ta.getDimensionPixelOffset(R.styleable.WidgetRoundImageView_widget_top_left_radius, 0);
            topLeftRadius = topLeftRadius > 0 ? topLeftRadius : allRadius;
            rids[0] = topLeftRadius;
            rids[1] = topLeftRadius;

            int topRightRadius = ta.getDimensionPixelOffset(R.styleable.WidgetRoundImageView_widget_top_right_radius, 0);
            topRightRadius = topRightRadius > 0 ? topRightRadius : allRadius;
            rids[2] = topRightRadius;
            rids[3] = topRightRadius;

            int bottomRightRadius = ta.getDimensionPixelOffset(R.styleable.WidgetRoundImageView_widget_bottom_right_radius, 0);
            bottomRightRadius = bottomRightRadius > 0 ? bottomRightRadius : allRadius;
            rids[4] = bottomRightRadius;
            rids[5] = bottomRightRadius;

            int bottomLeftRadius = ta.getDimensionPixelOffset(R.styleable.WidgetRoundImageView_widget_bottom_left_radius, 0);
            bottomLeftRadius = bottomLeftRadius > 0 ? bottomLeftRadius : allRadius;
            rids[6] = bottomLeftRadius;
            rids[7] = bottomLeftRadius;

            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        mPath.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }

    public void setAllRadius(int radius) {
        for (int i = 0; i < rids.length; i++) {
            rids[i] = radius;
        }
        postInvalidate();
    }

}
