package com.example.atest;

import android.app.Application;

import com.example.atest.adapter.tab.TabManager;
import com.example.atest.util.DistanceUtil;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DistanceUtil.init(this);
    }
}
