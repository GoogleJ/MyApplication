package com.example.headerlistview.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.headerlistview.adapter.main.MainRecommendAdapter;
import com.example.headerlistview.ui.widget.ItemList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 */

public class RecommendFragment extends BaseFragment {

    private List<String> data = new ArrayList<>();
    private MainRecommendAdapter adapter;

    public ItemList list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new MainRecommendAdapter(data);

        list = new ItemList(getActivity());
        list.setDivider(null);
        list.setScrollBarSize(0);
        list.setAdapter(adapter);

        return list;
    }

}
