package com.example.temp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    private View view_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        view_temp = findViewById(R.id.view_temp);
    }

    public void add(View view) {

    }

    public void back(View view) {

    }
}
