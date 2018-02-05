package com.example.headerlistview;
import android.app.Application;

import com.example.headerlistview.util.GlobalUtil;

/**
 * Created by Administrator on 2018/1/30.
 */

public class APP extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalUtil.context = this;
    }
}
