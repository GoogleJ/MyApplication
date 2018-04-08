package com.example.atest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
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

        final MainPageFragment fragment = new MainPageFragment();
        TabManager.fragments.add(fragment);
        manager.beginTransaction().add(R.id.main_container, fragment).commit();
        fragment.setOnCreateViewFinish(new MainPageFragment.onCreateViewFinish() {
            @Override
            public void onFinish() {
                Bitmap capture = tabAdapter.capture(fragment.root, DistanceUtil.getDisplayMetrics().widthPixels, DistanceUtil.getDisplayMetrics().heightPixels, Bitmap.Config.RGB_565);
                TabManager.defauleMainCapture = capture;
            }
        });
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
                int position = viewHolder.getAdapterPosition();
                manager.beginTransaction().remove(TabManager.fragments.get(viewHolder.getAdapterPosition())).commit();

                if (position == TabManager.currentTab) {
                    TabManager.currentTab = 0;
                } else if (TabManager.currentTab < position) {

                } else {
                    TabManager.currentTab -= 1;
                }

                TabManager.fragments.remove(position);

                if (TabManager.fragments.size() != 0) {
                    Fragment currentTab = TabManager.getCurrentTab();
                    if (currentTab != null) {
                        manager.beginTransaction().show(currentTab).commit();
                    }
                    tabAdapter.onDismiss(viewHolder.getAdapterPosition());
                } else {
                    addTab(null);
                }
            }

            private int screenWidth = DistanceUtil.getDisplayMetrics().widthPixels;

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

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                View itemView = viewHolder.itemView;
                itemView.setAlpha(1);
                itemView.setRotation(0);
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

        ll_main_front.setVisibility(View.VISIBLE);
        ll_main_back.setVisibility(View.INVISIBLE);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ll_main_front, "translationY", DistanceUtil.getRealScreenSize().y, 0);
        objectAnimator.setDuration(400);

        manager.beginTransaction().add(R.id.main_container, fragment).commit();
        if (TabManager.fragments.size() != 1) {
            manager.beginTransaction().hide(TabManager.getCurrentTab()).commit();
        }

        TabManager.currentTab = TabManager.fragments.size() - 1;
        tv_main_tabcount.setText("" + TabManager.fragments.size());
        tabAdapter.notifyItemInserted(TabManager.fragments.size() - 1);

        objectAnimator.start();
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        super.onBackPressed();
    }

    @Override
    public void onChangeFragment(int position) {
        if (position != TabManager.currentTab) {
            manager.beginTransaction().show(TabManager.fragments.get(position)).commit();
            manager.beginTransaction().hide(TabManager.getCurrentTab()).commit();
            TabManager.currentTab = position;
        }
        hideTab(null);
    }
}
