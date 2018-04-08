package com.example.headerlistview.ui.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/2/3.
 */

public class MainList extends ListView {

    public MainList(Context context) {
        super(context);
    }

    public MainList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isIntercept = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isIntercept) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (!isIntercept) {
                onInterceptTouchEvent(ev);
                return false;
            }
        }
        return super.onTouchEvent(ev);
    }
}
