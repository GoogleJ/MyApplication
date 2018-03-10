package com.example.temp1;

import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.temp1.layoutmanager.OverLayCardLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView tv_test;

    private LinearLayout ll_root;

    private RecyclerView recyclerview;

    private MyAdapter adapter;

    private List<String> data;

    private ItemTouchHelper touchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pages);

        tv_test = findViewById(R.id.tv_test);

        ll_root = findViewById(R.id.ll_root);

        recyclerview = findViewById(R.id.recyclerview);

        data = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            data.add("this is data " + i);
        }

        adapter = new MyAdapter(data);

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
        Snackbar.make(ll_root, "addPage", Snackbar.LENGTH_SHORT)
                .setAction("hide", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    public void select(View view) {
//        Drawable drawable = tv_test.getDrawable();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//        }
    }

    public void more(View view) {

    }
}

