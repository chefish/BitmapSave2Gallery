package com.fish.bitmapsave2gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

//把文件存到相册的 demo
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //checkAccouts();


            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝


            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }



//        ImageView iv = findViewById(R.id.aaa);
//        Uri uri = Uri.parse("content://media/external/images/media/131913");
//        InputStream inputStream = null;
//        try {
//            inputStream = getContentResolver().openInputStream(uri);
//            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
//            if( inputStream != null ) inputStream.close();
//            iv.setImageBitmap(bmp);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        findViewById(R.id.aa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(data, 0, data.length);
//                Bitmap bitmap = R.drawable.
//                save1();

//                Image image = getImageFromContentUri(s);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.skillop);
                ImgUtils.saveImageToGallery(MainActivity.this,bitmap);
            }
        });

    }

    private void save2(){
//        Bitmap icon = BitmapFactory.decodeResource(getResources(),
//                R.drawable.skillop);
//
//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
////        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));

    }

    private void save1() {
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.skillop);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), icon, "title", "description");
        Log.d(TAG, "insertImage " + path);
        Uri contentUri = Uri.parse(path);

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri));
    }

    // 提示用户该请求权限的弹出框

    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("由于雪诺需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用支付宝")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
//                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
