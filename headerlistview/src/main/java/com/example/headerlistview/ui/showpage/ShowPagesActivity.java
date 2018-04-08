package com.example.headerlistview.ui.showpage;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.headerlistview.APP;
import com.example.headerlistview.R;
import com.example.headerlistview.ui.MainActivity;


public class ShowPagesActivity extends AppCompatActivity {
    private LinearLayout ll_root;

    private RecyclerView recyclerview;

    private TextView tv_showpages_pages;

    private MyAdapter adapter;

    private ItemTouchHelper touchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pages);

        ll_root = findViewById(R.id.ll_root);

        tv_showpages_pages = findViewById(R.id.tv_showpages_pages);

        recyclerview = findViewById(R.id.recyclerview);

        adapter = new MyAdapter();

        recyclerview.setAdapter(adapter);

        recyclerview.setLayoutManager(new OverLayCardLayoutManager());

        touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                //todo 在这里可以拿到viewholder，可以设置接口让viewholder改变item的样式(触碰时)
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //todo 在这里可以拿到viewholder，可以设置接口让viewholder改变item的样式(放手后)
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                adapter.onMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                adapter.onDismiss(viewHolder.getAdapterPosition());
            }

            private int screenWidth = ScreenUtils.getScreenWidth();

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                float v = Math.abs(dX) / screenWidth;
                itemView.setAlpha(1 - v);
                itemView.setRotation(15 * (dX / (screenWidth / 2)));
                itemView.setTranslationX(dX);
                itemView.setTranslationY(Math.abs(dX) / 2);
            }
        });

        touchHelper.attachToRecyclerView(recyclerview);
    }

    public void addPage(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void select(View view) {
        Snackbar.make(ll_root, "select", Snackbar.LENGTH_SHORT)
                .setAction("hide", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    public void more(View view) {
        Snackbar.make(ll_root, "more", Snackbar.LENGTH_SHORT)
                .setAction("hide", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        tv_showpages_pages.setText(APP.pages.size() + "");
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
