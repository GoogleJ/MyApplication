package com.example.temp1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/2/26.
 */

public class CallLogUtil {

    @TargetApi(21)
    public List<CallRecord> queryCallLog(Activity activity) {
        List<CallRecord> data = new ArrayList<>();

        ContentResolver resolver = activity.getContentResolver();

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return data;
        }

        Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, new String[]{
                CallLog.Calls.CACHED_FORMATTED_NUMBER,
                CallLog.Calls.CACHED_MATCHED_NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.GEOCODED_LOCATION,
        }, null, null, "-date");


        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    CallRecord record = new CallRecord();
                    record.formatted_number = cursor.getString(0);
                    record.matched_number = cursor.getString(1);
                    record.name = cursor.getString(2);
                    record.type = getCallType(cursor.getInt(3));
                    record.date = formatDate(cursor.getLong(4));
                    record.duration = formatDuration(cursor.getLong(5));
                    record.location = cursor.getString(6);

                    data.add(record);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }

        return data;
    }

    private String getCallType(int anInt) {
        switch (anInt) {
            case CallLog.Calls.INCOMING_TYPE:
                return "呼入";
            case CallLog.Calls.OUTGOING_TYPE:
                return "呼出";
            case CallLog.Calls.MISSED_TYPE:
                return "未接";
            default:
                break;
        }
        return null;
    }

    String formatDate(long time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return format.format(new Date(time));
    }

    String formatDuration(long time) {
        long s = time % 60;
        long m = time / 60;
        long h = time / 60 / 60;
        StringBuilder sb = new StringBuilder();
        if (h > 0) {
            sb.append(h).append("小时");
        }
        if (m > 0) {
            sb.append(m).append("分");
        }
        sb.append(s).append("秒");
        return sb.toString();
    }

    class CallRecord {
        String date;
        String formatted_number;
        String matched_number;
        String name;
        String type;
        String location;
        String duration;
    }
}
