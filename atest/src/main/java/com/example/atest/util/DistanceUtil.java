package com.example.atest.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/1/29.
 */

public class DistanceUtil {
    private static Context context;

    public static Point getRealScreenSize() {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point realSize = new Point();
        display.getRealSize(realSize);
        return realSize;
    }

    public static int getStatusBarHeight() {
        int statusBarHeight1 = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics;
    }

    public static void init(Context context) {
        DistanceUtil.context = context;
    }

    // 根据手机的分辨率将dp的单位转成px(像素)
    public static int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 根据手机的分辨率将px(像素)的单位转成dp
    public static int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 将px值转换为sp值
    public static int px2sp(float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    // 将sp值转换为px值
    public static int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 屏幕宽度（像素）
    public static int getWindowWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    // 屏幕高度（像素）
    public static int getWindowHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
}
