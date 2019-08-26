package com.example.modelapp

import android.app.Application
import com.baidu.mapapi.SDKInitializer

import com.example.mvvm.config.Config
import com.example.mvvm.config.Constant

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Config.init(this)
                .add(Constant.API_HOST, "https://www.baidu.com/")
                .configure()

        SDKInitializer.initialize(this)
    }
}
