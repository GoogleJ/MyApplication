package com.example.headerlistview.ui.headerview;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.headerlistview.R;

/**
 * Created by Administrator on 2018/1/29.
 */

public class HeaderView1 implements View.OnClickListener {
    private View view;

    private LinearLayout root_header1;

    private RelativeLayout rl_main_topbar;

    private LinearLayout fakesearch;
    private LinearLayout nearby;

    private Activity activity;
    public HeaderView1(Activity activity) {
        this.activity = activity;
        getView();
    }

    public RelativeLayout getRl_main_topbar() {
        return rl_main_topbar;
    }

    public LinearLayout getFakesearch() {
        return this.fakesearch;
    }

    public LinearLayout getRootView() {
        return this.root_header1;
    }

    private void getView() {
        if (view == null) {
            view = View.inflate(activity, R.layout.main_header1, null);

            root_header1 = view.findViewById(R.id.root_header1);
            rl_main_topbar = view.findViewById(R.id.rl_main_topbar);

            fakesearch = view.findViewById(R.id.fakesearch);
            nearby = view.findViewById(R.id.nearby);

            fakesearch.setOnClickListener(this);
            nearby.setOnClickListener(this);
        }
    }

    public View getHeaderView() {
        if (view != null) {
            return view;
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fakesearch:
                Toast.makeText(activity, "fakesearch", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nearby:
                Toast.makeText(activity, "nearby", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
