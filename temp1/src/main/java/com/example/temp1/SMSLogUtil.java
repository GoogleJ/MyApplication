package com.example.temp1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/2/26.
 */

public class SMSLogUtil {
    @TargetApi(21)
    public List<SMSMessage> queryMessageLog(Activity activity) {
        List<SMSMessage> data = new ArrayList<>();

        ContentResolver resolver = activity.getContentResolver();
//        Telephony.Threads.CONTENT_URI
        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, new String[]{
                Telephony.Sms.ADDRESS,   //
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms.READ,
                Telephony.Sms.STATUS,
                Telephony.Sms.TYPE,
        }, null, null, "-date");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SMSMessage message = new SMSMessage();
                message.address = cursor.getString(0);
                message.body = cursor.getString(1);
                message.date = formatDate(cursor.getLong(2));
                message.read = getMessageRead(cursor.getInt(3));
                message.status = getMessageStatus(cursor.getInt(4));
                message.type = getMessageType(cursor.getInt(5));
                message.person = getPerson(message.address, activity);
                data.add(message);
            }
            cursor.close();
        }
        return data;
    }

    private String getMessageRead(int anInt) {
        if (1 == anInt) {
            return "已读";
        }
        if (0 == anInt) {
            return "未读";
        }
        return null;
    }

    private String getMessageType(int anInt) {
        if (1 == anInt) {
            return "收到的";
        }
        if (2 == anInt) {
            return "已发出";
        }
        return null;
    }

    private String getMessageStatus(int anInt) {
        switch (anInt) {
            case -1:
                return "接收";
            case 0:
                return "complete";
            case 64:
                return "pending";
            case 128:
                return "failed";
            default:
                break;
        }
        return null;
    }

    private String getPerson(String address, Activity activity) {
        try {
            ContentResolver resolver = activity.getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
            Cursor cursor;
            cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        String name = cursor.getString(0);
                        return name;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    String formatDate(long time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return format.format(new Date(time));
    }

    class SMSMessage {
        String date;
        String address;
        String body;
        String person;
        String read;
        String status;
        String type;
    }
}
