package com.example.atest;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView tv_test1;
    private TextView tv_test2;
    private TextView tv_test3;

    private TextView tv_test;
    private ImageView iv_test1;
    private ImageView iv_test2;

    private TextView tv_scale;
    private TextView tv_offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_test1 = findViewById(R.id.tv_test1);
        tv_test2 = findViewById(R.id.tv_test2);
        tv_test3 = findViewById(R.id.tv_test3);


        tv_test = findViewById(R.id.tv_test);
        iv_test1 = findViewById(R.id.iv_test1);
        iv_test2 = findViewById(R.id.iv_test2);

        tv_scale = findViewById(R.id.tv_scale);
        tv_offset = findViewById(R.id.tv_offset);
    }

    private int currentOffset = 0;

    public void offsetView1(View view) {
        currentOffset += 5;
        tv_test.offsetTopAndBottom(5);
        iv_test1.offsetTopAndBottom(5);
        iv_test2.offsetTopAndBottom(5);

        tv_offset.setText("offset: " + currentOffset);

        if (currentState == DRAWRECT) {
            drawRect(null);
        } else {
            hitRect(null);
        }
    }

    public void offsetView2(View view) {
        currentOffset -= 5;
        tv_test.offsetTopAndBottom(-5);
        iv_test1.offsetTopAndBottom(-5);
        iv_test2.offsetTopAndBottom(-5);

        tv_offset.setText("offset: " + currentOffset);

        if (currentState == DRAWRECT) {
            drawRect(null);
        } else {
            hitRect(null);
        }
    }

    private Rect rect = new Rect();

    private String rect2Info() {
        return "left: " + rect.left + ",top: " + rect.top + ",right: " + rect.right + ",bottom: " + rect.bottom;
    }

    public void hitRect(View view) {
        tv_test.getHitRect(rect);
        tv_test1.setText(rect2Info());

        iv_test1.getHitRect(rect);
        tv_test2.setText(rect2Info());

        iv_test2.getHitRect(rect);
        tv_test3.setText(rect2Info());

        currentState = HITRECT;
    }

    public void drawRect(View view) {
        tv_test.getDrawingRect(rect);
        tv_test1.setText(rect2Info());

        iv_test1.getDrawingRect(rect);
        tv_test2.setText(rect2Info());

        iv_test2.getDrawingRect(rect);
        tv_test3.setText(rect2Info());

        currentState = DRAWRECT;
    }

    private float currentScaleX = 1f;

    private static final int DRAWRECT = 1001;
    private static final int HITRECT = 1002;

    private int currentState = 0;

    public void addScaleX(View view) {
        if (currentScaleX + 0.1 <= 1) {
            currentScaleX += 0.1;

            tv_test.setScaleX(currentScaleX);
            iv_test1.setScaleX(currentScaleX);
            iv_test2.setScaleX(currentScaleX);

            tv_scale.setText("scale: " + currentScaleX);

            if (currentState == DRAWRECT) {
                drawRect(null);
            } else {
                hitRect(null);
            }
        }
    }

    public void deleteScaleX(View view) {
        if (currentScaleX - 0.1 >= 0) {
            currentScaleX -= 0.1;

            tv_test.setScaleX(currentScaleX);
            iv_test1.setScaleX(currentScaleX);
            iv_test2.setScaleX(currentScaleX);

            tv_scale.setText("scale: " + currentScaleX);

            if (currentState == DRAWRECT) {
                drawRect(null);
            } else {
                hitRect(null);
            }
        }
    }
}
