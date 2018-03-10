package com.example.headerlistview.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/2/3.
 */

public class MainList extends ListView {
    public boolean isIntercept = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isIntercept) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    public MainList(Context context) {
        super(context);
    }

    public MainList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
