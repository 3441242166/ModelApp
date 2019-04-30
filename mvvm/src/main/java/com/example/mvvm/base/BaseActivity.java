package com.example.mvvm.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity<V extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();

    protected V binding;
    protected VM viewModel;
    protected Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, getContentView());
        viewModel = getViewModel();
        initParam(savedInstanceState);
        initView();
        initEvent();
    }

    protected abstract VM getViewModel();

    private void initParam(Bundle savedInstanceState) {

    }

    protected abstract void initView();

    protected abstract void initEvent();

    public abstract int getContentView();

    protected void beforeOnCreate() {
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }
}
