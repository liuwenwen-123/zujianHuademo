package com.example.basic;

import android.app.Application;

import com.example.router.Arouter;

public class BaseApplication  extends Application {
//   public  static boolean is_application=BuildConfig.is_applicaion;
    @Override
    public void onCreate() {
        super.onCreate();
        Arouter.gerInstance().initRouter(this);
    }
}
