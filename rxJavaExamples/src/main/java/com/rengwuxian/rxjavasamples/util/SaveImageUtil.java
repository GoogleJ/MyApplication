package com.rengwuxian.rxjavasamples.util;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rengwuxian.rxjavasamples.App;
import com.rengwuxian.rxjavasamples.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class SaveImageUtil {
    private static final SaveImageUtil INSTANCE = new SaveImageUtil();

    private SaveImageUtil() {
    }

    private static File pictureFile;

    private Context context;
    private String url;

    public interface SaveResultListener {
        void success(boolean success);
    }

    private SaveResultListener saveResultListener;

    public static SaveImageUtil get() {
        if (pictureFile == null) {
            pictureFile = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "ZhuangBi");

            if (!pictureFile.exists()) {
                pictureFile.mkdirs();
            }
        }
        return INSTANCE;
    }

    public void show(Context context, String url, SaveResultListener saveResultListener) {
        this.context = context;
        this.url = url;
        this.saveResultListener = saveResultListener;

        new AlertDialog.Builder(context)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePic();
                    }
                })
                .setNegativeButton("取消", null)
                .setTitle("保存图片")
                .show();
    }

    private void savePic() {
        Glide.with(context)
                .asFile()
                .load(url)
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<File> target, boolean b) {
                        if (saveResultListener != null) {
                            saveResultListener.success(true);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File file, Object o, Target<File> target, DataSource dataSource, boolean b) {
                        if (file.exists()) {
                            try {
                                String[] split = url.split("/");

                                copyFileUsingFileChannels(file, new File(pictureFile, split[split.length - 1]));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (saveResultListener != null) {
                                saveResultListener.success(true);
                            }
                        }

                        return false;
                    }
                })
                .submit();
    }

    private void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            if (saveResultListener != null) {
                MediaScannerConnection.scanFile(context, new String[]{dest.getAbsolutePath()}, null, null);
                saveResultListener.success(true);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (saveResultListener != null) {
                saveResultListener.success(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (saveResultListener != null) {
                saveResultListener.success(false);
            }
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

//    public void shareImage(Context context, Bitmap bitmap, String packageName) {
//        try {
//            Uri uriToImage = Uri.parse(MediaStore.Images.Media.insertImage(
//                    context.getContentResolver(), bitmap, null, null));
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
//            shareIntent.setType("image/*");
//            // 遍历所有支持发送图片的应用。找到需要的应用
//            PackageManager packageManager = context.getPackageManager();
//            List<ResolveInfo> resolveInfoList = packageManager
//                    .queryIntentActivities(shareIntent,
//                            PackageManager.GET_RESOLVED_FILTER);
//            ComponentName componentName = null;
//            if (null == resolveInfoList) {
//                Toast.makeText(App.getInstance(), "未获取到应用列表", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            for (int i = 0; i < resolveInfoList.size(); i++) {
//                if (TextUtils.equals(
//                        resolveInfoList.get(i).activityInfo.packageName,
//                        packageName)) {
//                    componentName = new ComponentName(
//                            resolveInfoList.get(i).activityInfo.packageName,
//                            resolveInfoList.get(i).activityInfo.name);
//                    break;
//                }
//            }
//            // 已安装**
//            if (null != componentName) {
//                shareIntent.setComponent(componentName);
//                context.startActivity(shareIntent);
//            } else {
//                if (packageName == Constant.packageQQ) {
//                    Toast.makeText(App.getInstance(), "请先安装QQ", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(App.getInstance(), "请先安装微信", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception e) {
//            Toast.makeText(App.getInstance(), "分享失败", Toast.LENGTH_SHORT).show();
//        }
//    }

}
