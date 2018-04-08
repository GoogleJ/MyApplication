package com.example.recyclerdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private RelativeLayout rl_mask;
    ViewPropertyAnimator maskAnim;

    //    private RecyclerView recyclerView;
    private FrameLayout temptest;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rl_mask = findViewById(R.id.rl_mask);
//        recyclerView = findViewById(R.id.recyclerview);
        temptest = findViewById(R.id.temptest);

        ViewPropertyAnimator animate = temptest.animate();
        animate.setDuration(300);
        animate.scaleY(0.7f);
        animate.scaleX(0.7f);

        maskAnim = rl_mask.animate();
        maskAnim.setDuration(300);
        maskAnim.alpha(0f);
        maskAnim.start();
        animate.start();
    }

    public void back(View view) {
        ViewPropertyAnimator animate = temptest.animate();
        animate.setDuration(300);
        animate.scaleY(1f);
        animate.scaleX(1f);
        //mask变回白色
        maskAnim.alpha(1f);
        maskAnim.start();
        animate.start();

        handler.sendEmptyMessageDelayed(1, 300);
    }

    public void add(View view) {
        // Todo 这里加一个转场动画 overridePendingTransition();
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        finish();
    }

    public void jumpTo(View view) {
        Intent k = getPackageManager().getLaunchIntentForPackage("com.phonemonitortool");
        k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(k);
    }
}

