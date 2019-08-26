package com.example.mvvm.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvvm.helper.AppManager;
import com.jaeger.library.StatusBarUtil;


public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();

    protected VM viewModel;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(getContentView());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mContext = this;
        viewModel = getViewModel();
        initParam(savedInstanceState);
        initView();
        initEvent();
    }

    protected abstract VM getViewModel();

    protected void initParam(Bundle savedInstanceState) {

    }

    protected void initView() {

    }

    protected void initEvent() {

    }

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
