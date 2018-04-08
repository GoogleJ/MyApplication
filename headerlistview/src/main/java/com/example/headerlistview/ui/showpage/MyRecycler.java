package com.example.headerlistview.ui.showpage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/3/10.
 */

public class MyRecycler extends RecyclerView{
    public MyRecycler(Context context) {
        super(context);
    }

    public MyRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        return super.fling(velocityX, (int) (velocityY * 0.6));
    }
}
