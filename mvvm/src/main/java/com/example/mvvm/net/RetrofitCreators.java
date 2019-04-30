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

public class RetrofitCreators {
    private static final String TAG = "RetrofitCreators";

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Config.getConfigs(Constant.INTERCEPTOR);
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        private static OkHttpClient.Builder addInterceptor(){
            if(INTERCEPTORS !=null && !INTERCEPTORS.isEmpty()){
                for(Interceptor interceptor:INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Config.getConfigs(Constant.API_HOST);

        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }


    private static final class RxRetrofitServiceHolder{
        private static final RxRetrofitService RETROFIT_SERVICE =
                RetrofitHolder.RETROFIT.create(RxRetrofitService.class);
    }

    public static RxRetrofitService getRxRetrofitService(){
        return RxRetrofitServiceHolder.RETROFIT_SERVICE;
    }

}
