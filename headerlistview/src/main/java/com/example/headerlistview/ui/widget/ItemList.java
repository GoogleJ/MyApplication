package com.example.headerlistview.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/3/16.
 */

public class ItemList extends ListView {
    public ItemList(Context context) {
        super(context);
    }

    public ItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isHandle = false;

}
