package com.example.zujiandemo;

import com.example.basic.BaseApplication;
import com.example.router.Arouter;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Arouter.gerInstance().initRouter(this);
    }
}
