package com.example.atest.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atest.R;

import static android.content.ContentValues.TAG;

public class MainPageFragment extends Fragment {
    public View root;

    public interface onCreateViewFinish {
        void onFinish();
    }

    private onCreateViewFinish onCreateViewFinish;

    public void setOnCreateViewFinish(onCreateViewFinish onCreateViewFinish) {
        this.onCreateViewFinish = onCreateViewFinish;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        root = View.inflate(container.getContext(), R.layout.layout_mainpagefragment, null);

        if (onCreateViewFinish != null) {
            onCreateViewFinish.onFinish();
        }
        return root;
    }
}
