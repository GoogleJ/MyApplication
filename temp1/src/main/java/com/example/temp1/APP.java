package com.example.temp1;

import android.app.Activity;
import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.example.temp1.layoutmanager.OverLayCardLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class APP extends Application {
//    public static RecyclerView recyclerView;
//
//    public static List<Activity> activities = new ArrayList<>();
//
//    private OverLayCardLayoutManager overLayCardLayoutManager = new OverLayCardLayoutManager();

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);

//        View inflate = View.inflate(this, R.layout.item_recycler, null);
//        recyclerView = inflate.findViewById(R.id.recyclerview);
    }
}
