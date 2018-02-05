package wildcode.cuishou.ss;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.fileprovider.FileProvider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class MainActivity extends Activity implements View.OnLongClickListener {

    private ImageView iv_test;

    private File pictureFile;

    private static final int CLONE_DONE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CLONE_DONE) {
                File file = (File) msg.obj;
                MediaScannerConnection.scanFile(MainActivity.this, new String[]{file.getAbsolutePath()}, null, null);
                Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pictureFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Test");

        if (!pictureFile.exists()) {
            pictureFile.mkdir();
        }

        iv_test = findViewById(R.id.iv_test);

        iv_test.setOnLongClickListener(this);

        Glide.with(this)
                .load("https://www.zhuangbi.info/uploads/i/2018-01-07-add1d4d7681dbbf2bd5197c45a52d8c8.gif")
                .into(iv_test);
    }

    @Override
    public boolean onLongClick(View view) {
        new AlertDialog.Builder(this)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savaPic();
                    }
                })
                .setNegativeButton("取消", null)
                .setTitle("保存图片")
                .show();
        return true;
    }

    //保存图片到本地,根据data的结尾保存图片类型,通知系统扫描文件更新
    private void savaPic() {
        Glide.with(MainActivity.this)
                .asFile()
                .load("https://www.zhuangbi.info/uploads/i/2018-01-07-add1d4d7681dbbf2bd5197c45a52d8c8.gif")
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<File> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File file, Object o, Target<File> target, DataSource dataSource, boolean b) {
                        if (file.exists()) {
                            try {
                                copyFileUsingFileChannels(file, new File(pictureFile, "2018-01-07-add1d4d7681dbbf2bd5197c45a52d8c8.gif"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        return false;
                    }
                })
                .submit();
    }

    private void copyFileUsingFileChannels(final File source, final File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            Message message = handler.obtainMessage(CLONE_DONE, dest);
            handler.sendMessage(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

}
