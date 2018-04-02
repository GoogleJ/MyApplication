package com.example.atest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.atest.R;
import com.example.atest.util.DistanceUtil;

import java.security.acl.LastOwnerException;

import static android.content.ContentValues.TAG;

public class Tab extends RelativeLayout {

    private int height;
    private int width;

    private RelativeLayout rl_tab_title;

    private ImageView iv_tab_thumbnail;

    public Tab(Context context) {
        this(context, null);
    }

    public Tab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Tab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        height = DistanceUtil.getDisplayMetrics().heightPixels - DistanceUtil.dip2px(48) - DistanceUtil.getStatusBarHeight();
        width = DistanceUtil.getDisplayMetrics().widthPixels;
    }

    public void active(boolean active, Runnable runnable) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iv_tab_thumbnail = findViewById(R.id.iv_tab_thumbnail);
        rl_tab_title = findViewById(R.id.rl_tab_title);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
