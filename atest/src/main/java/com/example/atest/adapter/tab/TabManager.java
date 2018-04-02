package com.example.atest.adapter.tab;

import android.app.Fragment;

import com.example.atest.widget.Tab;

import java.util.ArrayList;
import java.util.List;

public class TabManager {
    public static int currentTab;

    public static List<Fragment> fragments = new ArrayList<>();

    public static Fragment getCurrentTab() {
        return fragments.get(currentTab);
    }

    public static void clear() {
        fragments.clear();
    }
}
