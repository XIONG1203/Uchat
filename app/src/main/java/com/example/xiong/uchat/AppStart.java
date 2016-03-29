package com.example.xiong.uchat;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by asus on 2015/12/11.
 */
public class AppStart extends Application {


    public static AppStart instatnce;
    //application初始化时候，可以是一个已经实例化好的一个类

    public void onCreate() {
        super.onCreate();
        instatnce = this;
        Fresco.initialize(instatnce);
    }

    public static synchronized AppStart getInstance() {
        return (instatnce);
    }


}
