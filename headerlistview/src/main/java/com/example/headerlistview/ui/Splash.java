package com.example.headerlistview.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.headerlistview.R;
import com.example.headerlistview.util.DistanceUtil;
import com.example.headerlistview.util.GlobalUtil;
import com.example.headerlistview.util.SPUtil;

public class Splash extends AppCompatActivity {

    private LinearLayout ll_splash_top;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        intent = new Intent(this, MainActivity.class);

        int anInt = SPUtil.getInt(SPUtil.SCREEN_HEIGHT, 0);
        int anInt1 = SPUtil.getInt(SPUtil.SCREEN_WIDTH, 0);

        if (anInt == 0 || anInt1 == 0) {
            int screenHeight = DistanceUtil.getWindowHeight(this);
            int screenWidth = DistanceUtil.getWindowWidth(this);
            SPUtil.putInt(SPUtil.SCREEN_HEIGHT, screenHeight);
            SPUtil.putInt(SPUtil.SCREEN_WIDTH, screenWidth);

            anInt = screenHeight;
            anInt1 = screenWidth;
        }

        GlobalUtil.screenheight = anInt;
        GlobalUtil.screenwidth = anInt1;

        int anInt2 = SPUtil.getInt(SPUtil.TOP_HEIGHT, 0);

        if (anInt2 == 0) {
            ll_splash_top = findViewById(R.id.ll_splash_top);
            ll_splash_top.post(new Runnable() {
                @Override
                public void run() {
                    SPUtil.putInt(SPUtil.TOP_HEIGHT, ll_splash_top.getHeight() + DistanceUtil.dip2px(Splash.this, 32.1f));
                    intent.putExtra("topheight", ll_splash_top.getHeight() + DistanceUtil.dip2px(Splash.this, 32.1f));
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            intent.putExtra("topheight", anInt2);
            startActivity(intent);
            finish();
        }
    }
}
