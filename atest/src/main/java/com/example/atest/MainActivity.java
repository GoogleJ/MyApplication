package com.example.atest;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.atest.adapter.tab.TabAdapter;
import com.example.atest.adapter.tab.TabManager;
import com.example.atest.fragment.MainPageFragment;
import com.example.atest.layoutmanager.OverLayCardLayoutManager;
import com.example.atest.util.DistanceUtil;

public class MainActivity extends AppCompatActivity implements TabAdapter.Bind2Mian {
    private static final String TAG = "MainActivity";

    private LinearLayout ll_main_front;
    private LinearLayout ll_main_back;
    private LinearLayout ll_main_back_bottom;

    private TextView tv_main_tabcount;

    private RecyclerView recycler_main_tab;

    private OverLayCardLayoutManager layoutManager;
    private TabAdapter tabAdapter;

    private FragmentManager manager;

    private ItemTouchHelper touchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        MainPageFragment fragment = new MainPageFragment();
        TabManager.fragments.add(fragment);
        manager.beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    private void initView() {
        touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
//                adapter.onDismiss(viewHolder.getAdapterPosition());
                }

            private int screenWidth = DistanceUtil.getDisplayMetrics().widthPixels;

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//                View itemView = viewHolder.itemView;
//                float v = Math.abs(dX) / screenWidth;
//                itemView.setAlpha(1 - v);
//                itemView.setRotation(15 * (dX / (screenWidth / 2)));
//                itemView.setTranslationX(dX);
//                itemView.setTranslationY(Math.abs(dX) / 2);
            }
        });

        manager = getFragmentManager();
        tabAdapter = new TabAdapter();
        tabAdapter.bind2Mian(this);
        layoutManager = new OverLayCardLayoutManager();

        ll_main_front = findViewById(R.id.ll_main_front);
        ll_main_back = findViewById(R.id.ll_main_back);
        ll_main_back_bottom = findViewById(R.id.ll_main_back_bottom);

        tv_main_tabcount = findViewById(R.id.tv_main_tabcount);

        recycler_main_tab = findViewById(R.id.recycler_main_tab);
        recycler_main_tab.setAdapter(tabAdapter);
        recycler_main_tab.setLayoutManager(layoutManager);

        touchHelper.attachToRecyclerView(recycler_main_tab);
    }

    public void showTab(View view) {
//        int childCount = recycler_main_tab.getChildCount();
//        Log.i(TAG, "showTab: " + childCount);
//        for (int i = 0; i < childCount; i++) {
//            View childAt = recycler_main_tab.getChildAt(i);
//            if (childAt == null) {
//                Log.i(TAG, "showTab: 为Null" + i);
//            }
//        }
        ll_main_front.setVisibility(View.INVISIBLE);
        ll_main_back.setVisibility(View.VISIBLE);

        TabAdapter.ViewHolder holder = (TabAdapter.ViewHolder) recycler_main_tab.findViewHolderForAdapterPosition(TabManager.currentTab);
        holder.iv_tab_thumbnail.setImageBitmap(tabAdapter.capture(TabManager.getCurrentTab().getView(), DistanceUtil.getDisplayMetrics().widthPixels, DistanceUtil.getDisplayMetrics().heightPixels, Bitmap.Config.RGB_565));
    }

    public void hideTab(View view) {
        ll_main_front.setVisibility(View.VISIBLE);
        ll_main_back.setVisibility(View.INVISIBLE);

        tv_main_tabcount.setText("" + TabManager.fragments.size());
    }

    public void addTab(View view) {
        MainPageFragment fragment = new MainPageFragment();
        TabManager.fragments.add(fragment);
        manager.beginTransaction().replace(R.id.main_container, fragment).commit();
        ll_main_front.setVisibility(View.VISIBLE);
        ll_main_back.setVisibility(View.INVISIBLE);
        tabAdapter.notifyItemInserted(TabManager.fragments.size() - 1);
        TabManager.currentTab = TabManager.fragments.size() - 1;

        tv_main_tabcount.setText("" + TabManager.fragments.size());
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        super.onBackPressed();
    }

    @Override

    public void onChangeFragment(int position) {
        manager.beginTransaction().replace(R.id.main_container, TabManager.fragments.get(position)).commit();
        TabManager.currentTab = position;

        ll_main_back.setVisibility(View.INVISIBLE);
        ll_main_front.setVisibility(View.VISIBLE);
    }
}
