package com.example.temp1;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Administrator on 2018/2/24.
 */

public class APP extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
