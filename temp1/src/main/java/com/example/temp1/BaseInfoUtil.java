package com.example.temp1;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Created by Administrator on 2018/2/24.
 */

public class BaseInfoUtil {

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String ipAddress = (String) msg.obj;
            Log.e("ipAddress", ipAddress);
        }
    };

    public static void getBase(Activity activity) {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //手机号1
        String phoneNumber1 = tm.getLine1Number();
        if (phoneNumber1 != null) {
            Log.e("phone1", phoneNumber1);
        }

        //手机号2
        String phoneNumber2 = tm.getGroupIdLevel1();
        if (phoneNumber2 != null) {
            Log.e("phone2", phoneNumber2);
        }

        //HuaWei
        String brand = Build.BRAND;
        Log.e("brand", brand);

        //Nexus 6P
        String model = Build.MODEL;
        Log.e("model", model);

        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        Log.e("name", deviceName);

        String manufacturer = Build.MANUFACTURER;
        Log.e("manufacturer", manufacturer);

        //Version
        String version = android.os.Build.ID;
        Log.e("version", version);

        //SDKVersion
        int sdkVersion = Build.VERSION.SDK_INT;
        Log.e("sdkVersion", sdkVersion + "");

        //deviceId 设备串号
        String deviceid = PhoneUtils.getIMEI();
        Log.e("deviceid", deviceid);

        //设备Mac
        String macAddress = getNewMac();
        Log.e("macAddress", macAddress);

        //开机时间
        long starting_time = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        Log.e("starting_time", "" + starting_time);

        long bootTime = SystemClock.elapsedRealtime();
        Log.e("bootTime", "" + bootTime);

        NetworkUtils.NetworkType network_type = NetworkUtils.getNetworkType();
        Log.e("network_type", network_type.name());

        getOutNetIP(); //异步请求 数据在handler里

        Intent batteryInfoIntent = activity.registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = batteryInfoIntent.getIntExtra("status", 0);
        int health = batteryInfoIntent.getIntExtra("health", 1);
        boolean present = batteryInfoIntent.getBooleanExtra("present", false);
        int level = batteryInfoIntent.getIntExtra("level", 0);
        int scale = batteryInfoIntent.getIntExtra("scale", 0);
        int plugged = batteryInfoIntent.getIntExtra("plugged", 0);
        int voltage = batteryInfoIntent.getIntExtra("voltage", 0);
        int temperature = batteryInfoIntent.getIntExtra("temperature", 0); // 温度的单位是10℃
        String technology = batteryInfoIntent.getStringExtra("technology");

        Log.e("status", "" + status);
        Log.e("health", "" + health);
        Log.e("present", "" + present);
        Log.e("level", "" + level);
        Log.e("scale", "" + scale);
        Log.e("plugged", "" + plugged);
        Log.e("voltage", "" + voltage);
        Log.e("temperature", "" + temperature + "℃"); //有误
        Log.e("technology", "" + technology);

        //sdk 21以上
//        BatteryManager batteryManager = (BatteryManager) activity.getSystemService(BATTERY_SERVICE);
//        System.out.println(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER));
//        System.out.println(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE));
//        System.out.println(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW));
    }

    public static void getCPU() {
        String cpuInfo = android.os.Build.CPU_ABI;
        Log.e("cpuInfo", cpuInfo);

        int coreNumber = getNumberOfCPUCores();
        Log.e("coreNumber", "" + coreNumber);

        String maxCpuFreq = getMaxCpuFreq();
        Log.e("maxCpuFreq", maxCpuFreq);

        String minCpuFreq = getMinCpuFreq();
        Log.e("minCpuFreq", minCpuFreq);
    }

    public static void getMemory(Context context) {
        String availMemory = getAvailMemory(context);
        Log.e("availMemory", "" + availMemory);

        String totalMemory = getTotalMemory(context);
        Log.e("totalMemory", "" + totalMemory);
    }

    public static void getStorage(Context context) {
        int sdCardStatus;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_BAD_REMOVAL)) {
            sdCardStatus = 1001;
        } else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_CHECKING)) {
            sdCardStatus = 1002;
        } else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            sdCardStatus = 1003;
        } else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdCardStatus = 1004;
        } else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_REMOVED)) {
            sdCardStatus = 1005;
        } else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_SHARED)) {
            sdCardStatus = 1006;
        } else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_UNMOUNTABLE)) {
            sdCardStatus = 1007;
        } else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_UNMOUNTED)) {
            sdCardStatus = 1008;
        }
//        else if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_UNKNOWN)) {
//            sdCardStatus = 0;
//        }
        else {
            sdCardStatus = 0;
        }

        Log.e("sdCardStatus", "" + sdCardStatus);

        String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e("sdCardPath", sdCardPath);

        StatFs sdcardSta = new StatFs(Environment.getExternalStorageDirectory().getPath());
        StatFs romSta = new StatFs(Environment.getDataDirectory().getPath());

        String totalSD = Formatter.formatFileSize(context, sdcardSta.getBlockSizeLong() * sdcardSta.getBlockCountLong());
        Log.e("totalSD", totalSD);
        String availableSD = Formatter.formatFileSize(context, sdcardSta.getBlockSizeLong() * sdcardSta.getAvailableBlocksLong());
        Log.e("availableSD", availableSD);

        String totalRom = Formatter.formatFileSize(context, romSta.getBlockSizeLong() * romSta.getBlockCountLong());
        Log.e("totalRom", totalRom);
        String availableRom = Formatter.formatFileSize(context, romSta.getBlockSizeLong() * romSta.getAvailableBlocksLong());
        Log.e("availableRom", availableRom);

        String romPath = Environment.getDataDirectory().getAbsolutePath();
        Log.e("romPath", romPath);
    }

    public static void getDisplay() {
        int screenWidth = ScreenUtils.getScreenWidth();
        Log.e("screenWidth", "" + screenWidth);

        int screenHeight = ScreenUtils.getScreenHeight();
        Log.e("screenHeight", "" + screenHeight);

        int screenDensityDpi = ScreenUtils.getScreenDensityDpi();
        Log.e("screenDensityDpi", "" + screenDensityDpi);
    }

    public static void getAppsInfo() {
        List<AppUtils.AppInfo> appsInfo = AppUtils.getAppsInfo();

        for (int i = 0; i < appsInfo.size(); i++) {
            Log.e(appsInfo.get(i).getName(), appsInfo.get(i).toString());
        }
    }

    // 获取CPU最大频率（单位KHZ）
    private static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }

        long l = Long.parseLong(result.trim());
        l /= 1000;

        return String.valueOf(l) + "MHZ";
    }

    // 获取CPU最小频率（单位KHZ）
    private static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }

        long l = Long.parseLong(result.trim());
        l /= 1000;

        return String.valueOf(l) + "MHZ";
    }

    // 获取可用内存
    private static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        return Formatter.formatFileSize(context, mi.availMem);
    }

    // 获取总内存
    private static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";
        String str2 = "";
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            String[] split = str2.split(":");
            String s = split[1];

            s.trim();

            String substring = s.substring(0, s.length() - 2);

            initial_memory = Long.valueOf(substring.trim()) * 1000; //或1024

            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);
    }

    // 获取CPU核数
    private static int getNumberOfCPUCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            return 1;
        }
        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            cores = -1;
        } catch (NullPointerException e) {
            cores = -1;
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();

            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

    // 获取设备Mac
    private static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    // 获取设备外网地址
    private static void getOutNetIP() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader buff = null;
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("http://pv.sohu.com/cityjson");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(5000);//读取超时
                    urlConnection.setConnectTimeout(5000);//连接超时
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);

                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                        InputStream is = urlConnection.getInputStream();
                        buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                        StringBuilder builder = new StringBuilder();
                        String line = null;
                        while ((line = buff.readLine()) != null) {
                            builder.append(line);
                        }

                        buff.close();//内部会关闭 InputStream
                        urlConnection.disconnect();

                        //截取字符串
                        int satrtIndex = builder.indexOf("{");//包含[
                        int endIndex = builder.indexOf("}");//包含]
                        String json = builder.substring(satrtIndex, endIndex + 1);//包含[satrtIndex,endIndex)
                        JSONObject jo = new JSONObject(json);
                        String ip = jo.getString("cip");

                        Message message = handler.obtainMessage();

                        message.obj = ip;

                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
