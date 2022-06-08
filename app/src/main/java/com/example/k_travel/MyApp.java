package com.example.k_travel;

import android.app.Application;
import android.content.Context;

import com.example.k_travel.been.User;
import com.example.k_travel.utils.AppLoginUtil;


public class MyApp extends Application {

    private static Context context;
    private volatile static MyApp instance;
    public static User mUserLogin = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MyApp.context = getApplicationContext();
        //设置推送
        if (AppLoginUtil.hasLogin()) {
            mUserLogin = AppLoginUtil.createLoginModel();
        } else {
            mUserLogin = null;
        }
    }


    public static Context getAppContext() {
        return MyApp.context;
    }

    public static MyApp getInstance() {
        if (instance == null) {
            synchronized (MyApp.class) {
                if (instance == null) {
                    instance = new MyApp();
                }
            }
        }
        return instance;
    }
}
