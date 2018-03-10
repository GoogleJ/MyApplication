package com.example.headerlistview.ui.headerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.headerlistview.R;
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

/**
 * Created by Administrator on 2018/1/29.
 */

public class HeaderView3 {
    private View view;

    private Context context;

    private MagicIndicator magic_faketab;

    public HeaderView3(Context context) {
        this.context = context;
        getView();
    }

    private void getView() {
        if (view == null) {
            view = View.inflate(context, R.layout.main_header3, null);

            magic_faketab = view.findViewById(R.id.magic_faketab);
        }
    }

    public View getHeaderView() {
        if (view != null) {
            return view;
        }
        return null;
    }

    public MagicIndicator getTab() {
        return magic_faketab;
    }

}
