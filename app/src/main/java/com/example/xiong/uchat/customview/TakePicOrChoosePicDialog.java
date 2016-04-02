package com.example.xiong.uchat.customview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;

import com.example.xiong.uchat.R;
import com.example.xiong.uchat.Util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;


/**
 * Created by 蝎子莱莱123 on 2016/1/9.
 */
public class TakePicOrChoosePicDialog {
    public static Uri imageUri;
    public static Uri imageUri2;
    public static File file;
    public static File file2;


    public static void dialog(final Context context, final Activity activity, final Boolean isBack) {
        final String items[] = {"拍照", "本地图片"};
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();


        alertDialog.getWindow().setContentView(R.layout.takepic_choose_dialog_layout);
        alertDialog.getWindow().findViewById(R.id.take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri2);
                activity.startActivityForResult(takePhoto, 1);
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().findViewById(R.id.choose_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                activity.startActivityForResult(intent, 2);
                alertDialog.dismiss();
            }
        });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                if (isBack) activity.finish();
            }
        });
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                dialog.dismiss();
                if (isBack)
                    activity.finish();
                return false;
            }
        });
        file = new FileUtil().openCacheFile(new Date().getTime() + ".jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        file2 = new FileUtil().openCacheFile(new Date().getTime() + ".jpg");
        try {
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(file);
        imageUri2 = Uri.fromFile(file2);
    }
}
