package com.example.headerlistview.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/2/3.
 */

public class SPUtil {
    private static final String spFileName = "app";

    public static final String FIRST_OPEN = "first_open";

    public static final String SCREEN_WIDTH = "screen_width";
    public static final String SCREEN_HEIGHT = "screen_height";

    public static final String TOP_HEIGHT = "top_height";

    public static String getString(String strKey) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, "");
        return result;
    }

    public static String getString(String strKey, String strDefault) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, strDefault);
        return result;
    }

    public static void putString(String strKey, String strData) {
        SharedPreferences activityPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(strKey, strData);
        editor.apply();
    }

    public static Boolean getBoolean(String strKey) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, false);
        return result;
    }

    public static Boolean getBoolean(String strKey,
                                     Boolean strDefault) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }


    public static void putBoolean(String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.apply();
    }

    public static int getInt(String strKey) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, -1);
        return result;
    }

    public static int getInt(String strKey, int strDefault) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, strDefault);
        return result;
    }

    public static void putInt(String strKey, int strData) {
        SharedPreferences activityPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt(strKey, strData);
        editor.apply();
    }

    public static float getFloat(String strKey) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        float result = setPreferences.getFloat(strKey, -1);
        return result;
    }

    public static float getFloat(String strKey, float strDefault) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        float result = setPreferences.getFloat(strKey, strDefault);
        return result;
    }

    public static void putFloat(String strKey, float strData) {
        SharedPreferences activityPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putFloat(strKey, strData);
        editor.apply();
    }

    public static long getLong(String strKey) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, -1);
        return result;
    }

    public static long getLong(String strKey, long strDefault) {
        SharedPreferences setPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, strDefault);
        return result;
    }

    public static void putLong(String strKey, long strData) {
        SharedPreferences activityPreferences = GlobalUtil.context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putLong(strKey, strData);
        editor.apply();
    }
}
