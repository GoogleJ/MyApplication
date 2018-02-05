package com.example.headerlistview.ui.headerview;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.headerlistview.R;
import com.example.headerlistview.adapter.main.GrideAdapter;


/**
 * Created by Administrator on 2018/1/29.
 */

public class HeaderView2 {
    private View view;

    private ViewPager pager_main;

    private PagerAdapter adapter;

    private Activity activity;

    public HeaderView2(Activity activity) {
        this.activity = activity;
        getView();
    }

    private void getView() {
        if (view == null) {
            view = View.inflate(activity, R.layout.main_header2, null);
            pager_main = view.findViewById(R.id.pager_main_header2);

            adapter = new PagerAdapter() {
                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    GridView gridView = new GridView(activity);
                    gridView.setScrollBarSize(0);
                    gridView.setNumColumns(6);

                    gridView.setAdapter(new GrideAdapter(activity));

                    container.addView(gridView);

                    return gridView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }

                @Override
                public int getCount() {
                    return 3;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }
            };

            pager_main.setAdapter(adapter);
        }
    }

    public View getHeaderView() {
        if (view != null) {
            return view;
        }
        return null;
    }

}
