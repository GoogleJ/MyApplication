package com.example.atest.adapter.tab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.atest.R;
import com.example.atest.util.DistanceUtil;
import com.example.atest.widget.Tab;

import static android.content.ContentValues.TAG;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> {

    public interface Bind2Mian {
        void onChangeFragment(int position);
    }

    private Bind2Mian bind2Mian;

    public void bind2Mian(Bind2Mian bind2Mian) {
        this.bind2Mian = bind2Mian;
    }

    @NonNull
    @Override
    public TabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tab, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bind2Mian != null) {
                    bind2Mian.onChangeFragment(i);
                }
            }
        });

        View view = TabManager.fragments.get(i).getView();
        Bitmap capture = capture(view, DistanceUtil.getDisplayMetrics().widthPixels, DistanceUtil.getDisplayMetrics().heightPixels, Bitmap.Config.RGB_565);
        viewHolder.iv_tab_thumbnail.setImageBitmap(capture);
    }

    @Override
    public int getItemCount() {
        return TabManager.fragments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_tab_title;
        public ImageView iv_tab_thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_tab_title = itemView.findViewById(R.id.rl_tab_title);
            iv_tab_thumbnail = itemView.findViewById(R.id.iv_tab_thumbnail);
        }
    }

    public Bitmap capture(View view, float width, float height, Bitmap.Config config) {
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }

        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, config);
        bitmap.eraseColor(Color.WHITE);

        Canvas canvas = new Canvas(bitmap);

        int status = canvas.save();

        int left = view.getScrollX();
        int top = view.getScrollY();

        canvas.translate(-left, -top);

        view.draw(canvas);
        canvas.restoreToCount(status);

        canvas.setBitmap(null);

        return bitmap;
    }
}
