package com.example.headerlistview.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.headerlistview.R;
import com.example.headerlistview.ui.fragment.CircleFragment;
import com.example.headerlistview.ui.fragment.RecommendFragment;
import com.example.headerlistview.ui.fragment.ShoppingFragment;
import com.example.headerlistview.ui.fragment.VideoFragment;
import com.example.headerlistview.util.MainTabPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private MagicIndicator magic;

    private ViewPager pager;

    private String[] mDataList = {"推荐", "视频", "圈子", "购物"};

    private List<String> titles = Arrays.asList(mDataList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        magic = findViewById(R.id.magic);

        pager = findViewById(R.id.pager);


        pager.setAdapter(new android.support.v13.app.FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public android.app.Fragment getItem(int i) {
                switch (i) {
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

        magic.setBackgroundColor(Color.parseColor("#f5f5f5"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : titles.size();
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
        });

        magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic, pager);
    }
}
