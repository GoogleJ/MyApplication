package com.example.headerlistview.ui.headerview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.headerlistview.R;

/**
 * Created by Administrator on 2018/1/29.
 */

public class HeaderView3 implements View.OnClickListener {
    private View view;

    private TextView tv_faketab1;
    private TextView tv_faketab2;
    private TextView tv_faketab3;
    private TextView tv_faketab4;

    private Context context;

    public HeaderView3(Context context) {
        this.context = context;
        getView();
    }

    private void getView() {
        if (view == null) {
            view = View.inflate(context, R.layout.main_header3, null);

            tv_faketab1 = view.findViewById(R.id.tv_faketab1);
            tv_faketab2 = view.findViewById(R.id.tv_faketab2);
            tv_faketab3 = view.findViewById(R.id.tv_faketab3);
            tv_faketab4 = view.findViewById(R.id.tv_faketab4);

            tv_faketab1.setOnClickListener(this);
            tv_faketab2.setOnClickListener(this);
            tv_faketab3.setOnClickListener(this);
            tv_faketab4.setOnClickListener(this);
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
            case R.id.tv_faketab1:
                Toast.makeText(context, "tv_faketab1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_faketab2:
                Toast.makeText(context, "tv_faketab2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_faketab3:
                Toast.makeText(context, "tv_faketab3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_faketab4:
                Toast.makeText(context, "tv_faketab4", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
