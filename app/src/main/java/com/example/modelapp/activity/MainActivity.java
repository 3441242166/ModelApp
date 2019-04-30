package com.example.modelapp.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.Toast;

import com.example.modelapp.R;
import com.example.modelapp.databinding.ActivityMainBinding;
import com.example.modelapp.model.MainViewModel;
import com.example.mvvm.base.BaseActivity;
import com.example.mvvm.net.rx.RxRetrofitClient;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {


    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void initView() {

        RxRetrofitClient.builder()
                .url("")
                .build()
                .get()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept: " + s);
                        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(mContext,throwable.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

}
