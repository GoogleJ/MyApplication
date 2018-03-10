package com.example.headerlistview.adapter.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.headerlistview.R;
import com.example.headerlistview.ui.fragment.CircleFragment;
import com.example.headerlistview.ui.fragment.RecommendFragment;
import com.example.headerlistview.ui.fragment.ShoppingFragment;
import com.example.headerlistview.ui.fragment.VideoFragment;
import com.example.headerlistview.util.GlobalUtil;
import com.example.headerlistview.util.MainTabPagerTitleView;
import com.example.headerlistview.util.SystemUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MainListAdapter extends BaseAdapter implements ViewPager.OnPageChangeListener {
    private FragmentManager manager;
    private Context context;

    private int topHeight;

    private MagicIndicator magic_faketab;
    private MagicIndicator magic_realtab;

    public MainListAdapter(FragmentManager manager, int topHeight, MagicIndicator magic_faketab, MagicIndicator magic_realtab) {
        this.manager = manager;
        this.topHeight = topHeight;
        this.magic_faketab = magic_faketab;
        this.magic_realtab = magic_realtab;
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

        initMagic(pager);

        pager.addOnPageChangeListener(this);

        return convertView;
    }

    private String[] title = {"推荐", "视频", "圈子", "购物"};

    private List<String> titles = Arrays.asList(title);

    private void initMagic(final ViewPager pager) {

        magic_faketab.setBackgroundColor(Color.parseColor("#ffffff"));

        CommonNavigator commonNavigator1 = new CommonNavigator(context);
        commonNavigator1.setAdjustMode(true);

        CommonNavigator commonNavigator2 = new CommonNavigator(context);
        commonNavigator2.setAdjustMode(true);

        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                MainTabPagerTitleView simplePagerTitleView = new MainTabPagerTitleView(context);
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setNormalColor(Color.parseColor("#666666"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#242424"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(index, true);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setXOffset(UIUtil.dip2px(context, 16));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(1.5f));
                indicator.setLineHeight(UIUtil.dip2px(context, 0.8));
                indicator.setColors(Color.parseColor("#242424"));
                return indicator;
            }
        };

        commonNavigator1.setAdapter(commonNavigatorAdapter);
        commonNavigator2.setAdapter(commonNavigatorAdapter);

        magic_faketab.setNavigator(commonNavigator1);
        magic_realtab.setNavigator(commonNavigator2);
        ViewPagerHelper.bind(magic_faketab, pager);
        ViewPagerHelper.bind(magic_realtab, pager);
    }

    private int currentpage = 0;

    public interface OnPageChange {
        void onPageChange();
    }

    private OnPageChange onPageChange;

    public void setOnPageChange(OnPageChange onPageChange) {
        this.onPageChange = onPageChange;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i != currentpage) {
            currentpage = i;
        }

        if (onPageChange != null) {
            onPageChange.onPageChange();
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
