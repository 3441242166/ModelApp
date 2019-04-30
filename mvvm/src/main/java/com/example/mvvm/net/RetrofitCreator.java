package com.example.mvvm.net;

import com.example.mvvm.config.Config;
import com.example.mvvm.config.Constant;
import com.example.mvvm.net.factory.ScalarsConverterFactory;
import com.example.mvvm.net.rx.RxRetrofitService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitCreator {
    private static final String TAG = "RetrofitCreator";

    private static final int TIME_OUT = 60;

    private ArrayList<Interceptor> interceptors;

    public RetrofitCreator addInterceptors(ArrayList<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    private OkHttpClient createOKHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ArrayList<Interceptor> configInterceptor = Config.getConfigs(Constant.INTERCEPTOR);

        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (configInterceptor != null) {
            for (Interceptor interceptor : configInterceptor) {
                builder.addInterceptor(interceptor);
            }
        }

        return builder
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private Retrofit createRetrofit() {
        String baseUrl = Config.getConfigs(Constant.API_HOST);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createOKHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public final RxRetrofitService getRxRetrofitService() {
        return createRetrofit().create(RxRetrofitService.class);
    }

}
