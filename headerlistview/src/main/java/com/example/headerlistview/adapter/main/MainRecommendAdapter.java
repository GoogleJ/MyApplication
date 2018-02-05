package com.example.headerlistview.adapter.main;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;

import com.example.headerlistview.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/3.
 */

public class MainRecommendAdapter extends BaseAdapter {

    private List<String> data;

    public MainRecommendAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data.size() == 0) {
            return 8;//lazy stub
        } else {
            return data.size();
        }
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

            convertView = View.inflate(parent.getContext(), R.layout.item_main_item, null);

            holder.stub = convertView.findViewById(R.id.stub_lazy);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (data.size() == 0) {
            if (holder.stub.getVisibility() != View.VISIBLE) {
                holder.stub.setVisibility(View.VISIBLE);
            }
        } else {
            holder.stub.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        ViewStub stub;
    }
}
