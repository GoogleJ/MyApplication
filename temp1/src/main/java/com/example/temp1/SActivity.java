package com.example.temp1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class SActivity extends AppCompatActivity {

    private FrameLayout main_content;

    private RelativeLayout rl_main_back;
    private RelativeLayout rl_main_front;

    private ViewPropertyAnimator aniFront;
    private ViewPropertyAnimator aniBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s);

        main_content = findViewById(R.id.main_content);

        rl_main_front = findViewById(R.id.rl_main_front);
        rl_main_back = findViewById(R.id.rl_main_back);

        aniFront = rl_main_front.animate();
        aniBack = rl_main_back.animate();

        aniFront.setDuration(300);
        aniBack.setDuration(300);
    }

    private final static int SHOW = 100;
    private final static int HIDE = 101;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW) {
                rl_main_front.setVisibility(View.GONE);
            } else if (msg.what == HIDE) {
                rl_main_back.setVisibility(View.GONE);
            }
        }
    };

    public void showSwitch(View view) {
        ViewPropertyAnimator animate = main_content.animate();

        animate.setDuration(300);

        animate.scaleY(0.7f);
        animate.scaleX(0.7f);

        aniFront.alpha(0f);
        aniBack.alpha(1f);

        rl_main_back.setVisibility(View.VISIBLE);

        animate.start();
        aniFront.start();
        aniBack.start();
        Message message = handler.obtainMessage(SHOW);
        handler.sendMessageDelayed(message, 300);
    }

    public void add(View view) {
        startActivity(new Intent(SActivity.this, SActivity.class));
    }

    public void back(View view) {
        ViewPropertyAnimator animate = main_content.animate();

        animate.setDuration(300);

        animate.scaleY(1f);
        animate.scaleX(1f);

        aniFront.alpha(1f);
        aniBack.alpha(0f);

        rl_main_front.setVisibility(View.VISIBLE);

        aniFront.start();
        aniBack.start();
        animate.start();
        Message message = handler.obtainMessage(HIDE);
        handler.sendMessageDelayed(message, 300);
    }
}
