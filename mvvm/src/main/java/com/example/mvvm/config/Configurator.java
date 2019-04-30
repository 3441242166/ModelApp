package com.example.mvvm.config;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

public class Configurator {

    private static final Map<Object, Object> CONFIGS = new WeakHashMap<>();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    {
        CONFIGS.put(Constant.IS_PREPARE, false);
    }

    private Configurator() {

    }

    static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final static Configurator getInstance() {
        return Holder.INSTANCE;
    }


    public final Map<Object, Object> getConfigs() {
        final boolean isPrepare =  (boolean) CONFIGS.get(Constant.IS_PREPARE);
        if (isPrepare){
            return CONFIGS;
        }
        throw new RuntimeException("Mast Prepare");
    }

    public final <T> T getConfigs(Object key) {

        return (T) getConfigs().get(key);
    }

    /*--------------------------------------------------------------------------------------------*/

    public final Configurator withApiHost(String host) {
        CONFIGS.put(Constant.API_HOST, host);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        CONFIGS.put(Constant.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(Constant.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator add(String key, Object val) {
        CONFIGS.put(key, val);
        return this;
    }

    public final void configure() {
        CONFIGS.put(Constant.IS_PREPARE, true);
    }
}
