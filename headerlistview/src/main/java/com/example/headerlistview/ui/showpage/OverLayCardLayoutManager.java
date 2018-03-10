package com.example.headerlistview.ui.showpage;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * Created by Administrator on 2018/3/3.
 */

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager {
    //保存所有的Item的上下左右的偏移量信息
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    //记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
    private SparseBooleanArray hasAttachedItems = new SparseBooleanArray();

    private int verticalScrollOffset = 0;
    private int totalScroll = 0;

    //item重叠数量
    private int overLapCount = 4;
    //item之间的间隙值
    private int overLapGap = ConvertUtils.dp2px(1);
    //重叠时item之间的间隙DP值
    private int itemGap = ConvertUtils.dp2px(200);

    //item数量
    private int itemCount = 0;

    //配置参数
    public void initConfig(int overLapCount, int itemGap, int overLapGap) {
        this.overLapCount = overLapCount;
        this.itemGap = itemGap;
        this.overLapCount = overLapGap;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    synchronized
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //实际要滑动的距离
        int travel = dy;

        if (verticalScrollOffset + dy <= 0) {
            //顶部
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalScroll) {
            //底部
            travel = totalScroll - verticalScrollOffset;
        }

//        offsetChildrenVertical(-travel);

        //移动以及堆叠的实现
        int overLapItemCount = (verticalScrollOffset + travel) / itemGap + 1;
        for (int i = 0; i < itemCount - overLapItemCount; i++) {
//            View viewForPosition = recycler.getViewForPosition(i + overLapItemCount);
            View viewForPosition = findViewByPosition(i + overLapItemCount);
            viewForPosition.offsetTopAndBottom(-travel);
        }

        verticalScrollOffset += travel;

        return travel;
    }

    //每次布局改变调用
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.e("onLayout", "enter");
        //重新获取itemcount并计算可滑动的距离
        itemCount = getItemCount();
        totalScroll = itemGap * (itemCount - 1);
//        totalScroll = itemGap * (itemCount - 1) - 3 * overLapGap; 堆叠效果

        //如果没有item，直接返回
        if (itemCount <= 0) return;
        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) {
            return;
        }

        detachAndScrapAttachedViews(recycler);

        int offsetY;
        int viewHeight = 0;
        for (int i = 0; i < itemCount; i++) {
            View view = recycler.getViewForPosition(i);

            addView(view);

            measureChildWithMargins(view, 0, 0);

            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);

            if (viewHeight == 0) {
                viewHeight = getDecoratedMeasurementVertical(view);
            }

            offsetY = itemGap * i;

            //滑动的时候是否让对应的item提高Z轴数值
            view.setTranslationZ(i * 22);

            layoutDecorated(view, widthSpace / 2, offsetY, widthSpace / 2 + getDecoratedMeasuredWidth(view)
                    , offsetY + viewHeight);
        }
    }

    /**
     * 获取某个childView在水平方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }
}