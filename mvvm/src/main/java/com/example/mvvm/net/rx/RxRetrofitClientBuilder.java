package com.example.mvvm.net.rx;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RxRetrofitClientBuilder {
    private static final String TAG = "RxRetrofitClientBuilder";

    private String mUrl;
    private Map<String, Object> mParams;
    private ArrayList<Interceptor> interceptors;
    private RequestBody mRequestBody;
    private File file;
    private MultipartBody.Part body;

    public final RxRetrofitClientBuilder url(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public final RxRetrofitClientBuilder file(String file) {
        this.file = new File(file);
        return this;
    }

    public final RxRetrofitClientBuilder file(File file) {
        this.file = file;
        return this;
    }

    public final RxRetrofitClientBuilder body(MultipartBody.Part body) {
        this.body = body;
        return this;
    }

    public final RxRetrofitClientBuilder params(Map<String, Object> mParams) {
        this.mParams = mParams;
        return this;
    }

    public final RxRetrofitClientBuilder params(String key, Object val) {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        this.mParams.put(key, val);
        return this;
    }

    public final RxRetrofitClientBuilder header(ArrayList<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public final RxRetrofitClientBuilder header(Interceptor interceptor) {
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }
        this.interceptors.add(interceptor);
        return this;
    }

    public final RxRetrofitClientBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    private void check() {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }

        for (String key : mParams.keySet()) {
            Log.i(TAG, "key = " + key + "  value = " + mParams.get(key));
        }

    }

    public final RxRetrofitClient build() {
        check();
        return new RxRetrofitClient(mUrl,
                mParams,
                interceptors,
                mRequestBody,
                file,
                body);
    }
}
