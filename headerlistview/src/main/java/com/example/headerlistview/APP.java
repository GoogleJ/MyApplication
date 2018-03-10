package com.example.headerlistview;

import android.app.Application;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.example.headerlistview.util.GlobalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class APP extends Application {

    public static List<View> pages = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        GlobalUtil.context = this;
    }
}
