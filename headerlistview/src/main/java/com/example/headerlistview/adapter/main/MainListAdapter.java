package com.example.headerlistview.adapter.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.headerlistview.R;
import com.example.headerlistview.ui.fragment.CircleFragment;
import com.example.headerlistview.ui.fragment.RecommendFragment;
import com.example.headerlistview.ui.fragment.ShoppingFragment;
import com.example.headerlistview.ui.fragment.VideoFragment;
import com.example.headerlistview.util.GlobalUtil;
import com.example.headerlistview.util.SystemUtil;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MainListAdapter extends BaseAdapter {
    private FragmentManager manager;
    private Context context;

    private int topHeight;

    public MainListAdapter(FragmentManager manager, int topHeight) {
        this.manager = manager;
        this.topHeight = topHeight;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (context == null) {
            context = parent.getContext();
        }

        int statusBarHeight = SystemUtil.getStatusBarHeight(context);

        int pagerHeight = GlobalUtil.screenheight - statusBarHeight - topHeight - SystemUtil.getNavationBarHeight(context);

        convertView = View.inflate(context, R.layout.item_main, null);

        ViewPager pager = convertView.findViewById(R.id.pager_main);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pagerHeight);
        pager.setLayoutParams(layoutParams);

        pager.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new RecommendFragment();
                    case 1:
                        return new VideoFragment();
                    case 2:
                        return new CircleFragment();
                    case 3:
                        return new ShoppingFragment();
                    default:
                        return new RecommendFragment();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        return convertView;
    }
}
