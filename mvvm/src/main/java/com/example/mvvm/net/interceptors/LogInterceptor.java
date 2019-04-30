package com.example.mvvm.net.interceptors;

import android.util.Log;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class LogInterceptor implements Interceptor {

    private static final String TAG = "LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.d(TAG, "----------Start----------------");
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        Log.d(TAG + "| url", request.url().toString());
        // Log.v(TAG+"| body", bodyToString(request));
        Log.v(TAG + "| body", URLDecoder.decode(bodyToString(request), "utf-8"));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.v(TAG + "| request", content);
        Log.d(TAG, "----------End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
