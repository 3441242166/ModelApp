package com.example.mvvm.net.rx;


import com.example.mvvm.net.HttpMethod;
import com.example.mvvm.net.RetrofitCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RxRetrofitClient {

    private final String URL;
    private final Map<String,Object> PARAMS;
    private final ArrayList<Interceptor> INTERCEPTORS;
    private final RequestBody BODY;
    private final File FILE;
    private final MultipartBody.Part PART;

    RxRetrofitClient(String url, Map<String, Object> params, ArrayList<Interceptor> interceptors,
                     RequestBody body, File file, MultipartBody.Part part) {
        URL = url;
        INTERCEPTORS = interceptors;
        PARAMS = params;
        BODY = body;
        FILE = file;
        PART = part;
    }

    public static RxRetrofitClientBuilder builder(){
        return new RxRetrofitClientBuilder();
    }

    private Observable<String> request(HttpMethod method){
        final RxRetrofitService service = new RetrofitCreator().addInterceptors(INTERCEPTORS).getRxRetrofitService();

        Observable<String> observable = null;

        switch (method){
            case GET:
                observable = service.get(URL,PARAMS);
                break;
            case POST:
                observable = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                if(PART == null){
                    final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                    final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                    observable =  service.upload(URL, body);
                }else {
                    observable =  service.upload(URL, PART);
                }
               break;
        }

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*--------------------------------------------------------------------------------------------*/

    public final Observable<String> get(){
        return request((HttpMethod.GET));
    }

    public final Observable<String> post(){
        if(BODY == null){
            return request((HttpMethod.POST));
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> delete(){
        return request((HttpMethod.DELETE));
    }

    public final Observable<String> put(){
        if(BODY == null){
            return request((HttpMethod.PUT));
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> upload(){
        return request(HttpMethod.UPLOAD);
    }

}
