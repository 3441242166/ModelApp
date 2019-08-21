package com.example.modelapp;

import android.app.Application;

import com.example.mvvm.config.Config;
import com.example.mvvm.config.Constant;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Config.init(this)
                .add(Constant.API_HOST,"https://www.baidu.com/")
                .configure();
    }
}
