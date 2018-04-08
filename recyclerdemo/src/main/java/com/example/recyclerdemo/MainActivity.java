package com.example.recyclerdemo;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fram_main;

    private TextView tv_main_pagecount;

    private TextView tv_tttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_tttt = findViewById(R.id.tv_tttt);
        tv_tttt.setText(SystemClock.currentThreadTimeMillis() + "");

        Content.activities.add(this);

        fram_main = findViewById(R.id.fram_main);

        tv_main_pagecount = findViewById(R.id.tv_main_pagecount);
        tv_main_pagecount.setText("" + Content.activities.size());
    }

    public void showSwitcher(View view) {
        overridePendingTransition(0, 0);

        startActivity(new Intent(MainActivity.this, Main2Activity.class));
    }

    @Override
    protected void onDestroy() {
        Content.activities.remove(this);
        super.onDestroy();
    }
}
