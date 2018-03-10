package com.example.temp1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements T {

    @Override
    public void onDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onMove(int from, int to) {
        Collections.swap(data, from, to);
        notifyItemMoved(from, to);
    }

    private Context context;

    private List<String> data;

    public MyAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.context == null) {
            context = viewGroup.getContext();
        }

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        String s = myViewHolder.tv_item_sitetitle.getText().toString();
        myViewHolder.tv_item_sitetitle.setText(s + i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_sitetitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_item_sitetitle = itemView.findViewById(R.id.tv_item_sitetitle);
//            itemView.setBackgroundColor(); 无法改变点击和放手时的颜色
            //Todo 这里目前可以使用CallBack的onSelectedChanged && clearView 方法
        }
    }
}
