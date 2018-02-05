package com.example.headerlistview.adapter.main;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.headerlistview.R;
import com.example.headerlistview.util.DistanceUtil;
import com.example.headerlistview.util.GlobalUtil;

/**
 * Created by Administrator on 2018/1/29.
 */

public class GrideAdapter extends BaseAdapter {
    private String[] texts = new String[]{"想听FM", "爱奇艺", "爱拍", "看热点", "小说", "视频", "腾讯", "话题圈", "百度", "搜狐", "京东", "更多"};

    private Activity activity;

    public GrideAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            int screenWidth = GlobalUtil.screenwidth;

            int viewWidth = screenWidth / 6;

            convertView = View.inflate(parent.getContext(), R.layout.item_gride, null);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewWidth, DistanceUtil.dip2px(activity, GlobalUtil.mainHeader2Height));

            LinearLayout v;
            v = (LinearLayout) convertView;
            if (position < 6) {
                v.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                convertView.setPadding(0, 0, 0, DistanceUtil.dip2px(activity, 16));
            } else {
                v.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                convertView.setPadding(0, DistanceUtil.dip2px(activity, 16), 0, 0);
            }

            convertView.setLayoutParams(params);

            holder.iv_main_header2 = convertView.findViewById(R.id.iv_main_header2);
            holder.tv_main_header2 = convertView.findViewById(R.id.tv_main_header2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_main_header2.setText(texts[position]);

        if (position % 2 == 0) {
            holder.iv_main_header2.setImageResource(R.drawable.gride1);
        } else {
            holder.iv_main_header2.setImageResource(R.drawable.gride2);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_main_header2;
        TextView tv_main_header2;
    }
}
