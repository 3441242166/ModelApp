package com.example.mvvm.config;

import android.content.Context;

import java.util.Map;
import java.util.WeakHashMap;

public final class Config {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .add(Constant.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Map<Object, Object> getConfigs() {
        return Configurator.getInstance().getConfigs();
    }

    public static <T> T getConfigs(Object key) {
        return (T) Configurator.getInstance().getConfigs(key);
    }

    public static Context getApplicationContext() {
        return (Context) Configurator.getInstance().getConfigs(Constant.APPLICATION_CONTEXT);
    }

}
