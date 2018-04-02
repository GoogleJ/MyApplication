package com.example.atest.layoutmanager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.atest.util.DistanceUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/3/3.
 */

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager {

    private int verticalScrollOffset = 0;
    private int totalScroll = 0;

    //item重叠数量
    private int overLapCount = 4;
    //item之间的间隙值
    private int overLapGap = DistanceUtil.dip2px(1);

    //item之间的间隙DP值
    private int itemGap = DistanceUtil.dip2px(200);

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
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //实际要滑动的距离
        int travel = dy;
        if (travel > 0) {
            for (int i = 0; i < itemCount - 1; i++) {
                View viewForPosition = findViewByPosition(i + 1);
                int currentOffset = (int) viewForPosition.getTag();
                int offsetToTop = itemGap * (i + 1) - currentOffset;
                int i1 = offsetToTop - travel;
                if (i1 < 0 && offsetToTop >= 0) {
                    viewForPosition.offsetTopAndBottom(-offsetToTop);
                    viewForPosition.setTag(itemGap * (i + 1));
                } else if (i1 > 0) {
                    viewForPosition.offsetTopAndBottom(-travel);
                    viewForPosition.setTag(currentOffset + travel);
                }
            }
        } else {
            int i1 = verticalScrollOffset + travel;
            for (int i = itemCount - 1; i > 0; i--) {
                View viewForPosition = findViewByPosition(i);
                int currentOffset = (int) viewForPosition.getTag();
                if (i1 > currentOffset) continue;
                if (travel <= -currentOffset && currentOffset != 0) {
                    viewForPosition.offsetTopAndBottom(currentOffset);
                    viewForPosition.setTag(0);
                } else if (currentOffset > 0) {
                    viewForPosition.offsetTopAndBottom(-travel);
                    viewForPosition.setTag(currentOffset + travel);
                }
            }
        }
        if (verticalScrollOffset + dy <= 0) {
            //顶部
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalScroll) {
            //底部
            travel = totalScroll - verticalScrollOffset;
        }
        verticalScrollOffset += travel;
        return travel;
    }

    //每次布局改变调用
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        verticalScrollOffset = 0;
        //重新获取itemcount并计算可滑动的距离
        itemCount = getItemCount();
        totalScroll = itemGap * (itemCount - 1);
//        totalScroll = itemGap * (itemCount - 1) - overLapCount * overLapGap; 堆叠效果

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

            view.setTag(0);

            measureChildWithMargins(view, 0, 0);

            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);

            if (viewHeight == 0) {
                viewHeight = DistanceUtil.getDisplayMetrics().heightPixels - DistanceUtil.dip2px(48) - DistanceUtil.getStatusBarHeight();
            }

            offsetY = itemGap * i;

            //滑动的时候是否让对应的item提高Z轴数值
            view.setTranslationZ(i * 20);

            layoutDecorated(view, 0, offsetY - DistanceUtil.dip2px(32), DistanceUtil.getDisplayMetrics().widthPixels
                    , offsetY + viewHeight);

//            view.setScaleY(0.65f);
//            view.setScaleX(0.7f);

        }
    }

    private int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }
}