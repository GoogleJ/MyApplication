package com.example.headerlistview.ui;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.headerlistview.APP;
import com.example.headerlistview.ui.headerview.HeaderView1;
import com.example.headerlistview.ui.headerview.HeaderView2;
import com.example.headerlistview.ui.headerview.HeaderView3;
import com.example.headerlistview.R;
import com.example.headerlistview.adapter.main.MainListAdapter;
import com.example.headerlistview.ui.showpage.ShowPagesActivity;
import com.example.headerlistview.ui.widget.MainList;
import com.example.headerlistview.util.DistanceUtil;
import com.example.headerlistview.util.SystemUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MainList list;
    private HeaderView1 header1;
    private HeaderView2 header2;
    private HeaderView3 header3;

    private ImageView tab1;
    private ImageView tab2;
    private ImageView tab3;
    private ImageView tab4;
    private ImageView tab5;
    private TextView tv_main_pagecount;

    private LinearLayout realsearch;
    private LinearLayout realtab;
    private MagicIndicator magic_realtab;

    //记录第一次显示需要滑动的最大距离
    private int firstScrollDistance;
    //记录第二次显示需要滑动的最大距离
    private int secondScrollDistance;

    private boolean secondScrollDistanceFlag; //判断两个distance是否计算完成

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int startChangeColorScrollY;
    private int startChangeColor;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                secondScrollDistance += (int) msg.obj;
            } else if (msg.what == 2) {
                secondScrollDistanceFlag = true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APP.pages.add(findViewById(R.id.main_root));

        SystemUtil.changeStatusBarColor(this, getResources().getColor(R.color.colorfake));

        initView();

        getNeedHeight();

        handleScroll();
    }

    private void handleScroll() {
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mCurrentfirstVisibleItem;
            private SparseArray recordSp = new SparseArray(0);//设置容器大小，默认是10

            private boolean firstAttach; //第一个停留是否激活
            private boolean secondAttach;//第二个停留是否激活
            private boolean isHeaderBacShow = true;//header的静态背景是否存在

            private int currentMargin = DistanceUtil.dip2px(MainActivity.this, 14);

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;
                View firstView = view.getChildAt(0);//获取当前最顶部的item
                if (firstView != null) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (itemRecord == null) {
                        itemRecord = new ItemRecod();
                    }

                    itemRecord.height = firstView.getHeight();//获取当前最顶部Item的高度
                    itemRecord.top = firstView.getTop();//获取对应item距离顶部的距离
                    recordSp.append(firstVisibleItem, itemRecord);//添加键值对设置值

                    if (firstScrollDistance == 0 || !secondScrollDistanceFlag) {
                        return;
                    }

                    int scrollY = getScrollY();

                    if (scrollY > firstScrollDistance) {
                        if (!firstAttach) {
                            firstAttach = true;
                            realsearch.setVisibility(View.VISIBLE);

                            SystemUtil.changeStatusBarColor(MainActivity.this, getResources().getColor(R.color.colorreal), false);
                        }
                        if (scrollY >= secondScrollDistance) {
                            if (!secondAttach) {
                                list.isIntercept = false;
                                secondAttach = true;
                                realtab.setVisibility(View.VISIBLE);
                                tab2.setImageResource(R.drawable.refresh);
                                tab2.setAlpha(1f);
                            }
                        } else if (scrollY < secondScrollDistance) {
                            if (secondAttach) {
                                list.isIntercept = true;
                                secondAttach = false;
                                realtab.setVisibility(View.GONE);
                                Animation animation = tab2.getAnimation();
                                if (animation != null && !animation.hasEnded()) {
                                    animation.cancel();
                                }
                                tab2.setImageResource(R.drawable.icon_right);
                                tab2.setAlpha(0.5f);
                            }
                        }
                    } else {
                        if (firstAttach) {
                            firstAttach = false;
                            realsearch.setVisibility(View.GONE);

                            SystemUtil.changeStatusBarColor(MainActivity.this, getResources().getColor(R.color.colorfake), true);
                        }

                        int margin = DistanceUtil.dip2px(MainActivity.this, (14f - (6f * scrollY / firstScrollDistance)));
                        if (currentMargin != margin) {
                            currentMargin = margin;
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) header1.getFakesearch().getLayoutParams();

                            layoutParams.setMargins(currentMargin, currentMargin, currentMargin, currentMargin);

                            header1.getFakesearch().setLayoutParams(layoutParams);
                        }

                        if (scrollY >= startChangeColorScrollY && startChangeColorScrollY != 0) {
                            if (isHeaderBacShow) {
                                isHeaderBacShow = false;
                                header1.getFakesearch().setElevation(DistanceUtil.dip2px(MainActivity.this, 3));
                            }
                            float f = ((float) (scrollY - startChangeColorScrollY) / DistanceUtil.dip2px(MainActivity.this, 10));
                            if (0 < f && f < 1) {
                                int color = (int) argbEvaluator.evaluate(f, startChangeColor, Color.parseColor("#ffffff"));
                                header1.getRootView().setBackgroundColor(color);
                            }
                        } else {
                            if (!isHeaderBacShow) {
                                isHeaderBacShow = true;
                                header1.getFakesearch().setElevation(0);
                                header1.getRootView().setBackgroundResource(R.drawable.main_bac);
                            }
                        }
                    }
                }
            }

            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                    ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                    if (itemRecod != null) {
                        height += itemRecod.height;
                    }
                }
                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                if (null == itemRecod) {
                    itemRecod = new ItemRecod();
                }
                return height - itemRecod.top;
            }

            class ItemRecod {
                int height = 0;
                int top = 0;
            }
        });
    }

    private void getNeedHeight() {
        header1.getRl_main_topbar().post(new Runnable() {
            @Override
            public void run() {
                firstScrollDistance = header1.getRl_main_topbar().getHeight() + DistanceUtil.dip2px(MainActivity.this, 24);

                startChangeColorScrollY = firstScrollDistance - DistanceUtil.dip2px(MainActivity.this, 10);
                startChangeColor = (int) argbEvaluator.evaluate((float) startChangeColorScrollY / firstScrollDistance, Color.parseColor("#2F8AE8"), Color.parseColor("#64B2EE"));

                handler.sendMessage(handler.obtainMessage(1, firstScrollDistance));
            }
        });

        header2.getHeaderView().post(new Runnable() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage(1, header2.getHeaderView().getHeight()));
                handler.sendMessage(handler.obtainMessage(2));
            }
        });
    }

    private MainListAdapter mainListAdapter;

    private void initView() {
        tab1 = findViewById(R.id.iv_tab1);
        tab2 = findViewById(R.id.iv_tab2);
        tab3 = findViewById(R.id.iv_tab3);
        tab4 = findViewById(R.id.iv_tab4);
        tab5 = findViewById(R.id.iv_tab5);
        tv_main_pagecount = findViewById(R.id.tv_main_pagecount);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);

        realsearch = findViewById(R.id.realsearch);
        realsearch.setOnClickListener(this);

        realtab = findViewById(R.id.realtab);

        magic_realtab = findViewById(R.id.magic_realtab);

        list = findViewById(R.id.list);

        header1 = new HeaderView1(this);
        header2 = new HeaderView2(this);
        header3 = new HeaderView3(this);
        list.addHeaderView(header1.getHeaderView());
        list.addHeaderView(header2.getHeaderView());
        list.addHeaderView(header3.getHeaderView());

        mainListAdapter = new MainListAdapter(getFragmentManager(), getIntent().getIntExtra("topheight", 0), header3.getTab(), magic_realtab);

        mainListAdapter.setOnPageChange(new MainListAdapter.OnPageChange() {
            @Override
            public void onPageChange() {
                if (list.getFirstVisiblePosition() != 2) {
                    list.smoothScrollToPositionFromTop(2, 0);
                }
            }
        });

        list.setAdapter(mainListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tab1:
                Toast.makeText(getApplicationContext(), "tab1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_tab2:
                if (realtab.getVisibility() == View.VISIBLE) {
                    Interpolator overshootInterpolator = new OvershootInterpolator();
                    RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setInterpolator(overshootInterpolator);
                    animation.setDuration(1000);

                    tab2.startAnimation(animation);
                } else {
                    Toast.makeText(getApplicationContext(), "tab1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_tab3:
                Toast.makeText(getApplicationContext(), "tab3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_tab4:
                list.smoothScrollToPosition(0);
                list.isIntercept = true;
                break;
            case R.id.iv_tab5:
                Toast.makeText(getApplicationContext(), "tab5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.realsearch:
                Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void showPages(View view) {
        startActivity(new Intent(this, ShowPagesActivity.class));
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}



