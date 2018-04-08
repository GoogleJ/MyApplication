package com.example.headerlistview.ui.showpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.example.headerlistview.APP;
import com.example.headerlistview.R;

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

    private List<View> data;

    public MyAdapter() {
        this.data = APP.pages;
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

        View view = data.get(i);
        Bitmap capture = capture(view, ConvertUtils.dp2px(388), ConvertUtils.dp2px(502), Bitmap.Config.RGB_565);
        myViewHolder.iv_item_thumbnail.setImageBitmap(capture);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_sitetitle;

        private ImageView iv_item_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_item_sitetitle = itemView.findViewById(R.id.tv_item_sitetitle);

            iv_item_thumbnail = itemView.findViewById(R.id.iv_item_thumbnail);
//            itemView.setBackgroundColor(); 无法改变点击和放手时的颜色
            //Todo 这里目前可以使用CallBack的onSelectedChanged && clearView 方法
        }
    }

    private Bitmap capture(View view, float width, float height, Bitmap.Config config) {
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }

        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, config);
        bitmap.eraseColor(Color.WHITE);

        Canvas canvas = new Canvas(bitmap);
        int left = view.getLeft();
        int top = view.getTop();
//        if (scroll) {
//            left = view.getScrollX();
//            top = view.getScrollY();
//        }
        int status = canvas.save();
        canvas.translate(-left, -top);

        float scale = width / view.getWidth();
        canvas.scale(scale, scale, left, top);

        view.draw(canvas);
        canvas.restoreToCount(status);

        Paint alphaPaint = new Paint();
        alphaPaint.setColor(Color.TRANSPARENT);

//        canvas.drawRect(0f, 0f, 1f, height, alphaPaint);
//        canvas.drawRect(width - 1f, 0f, width, height, alphaPaint);
//        canvas.drawRect(0f, 0f, width, 1f, alphaPaint);
//        canvas.drawRect(0f, height - 1f, width, height, alphaPaint);
        canvas.setBitmap(null);

        return bitmap;
    }
}
