package com.example.mvvm.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {
    private final String TAG = getClass().getSimpleName();

    protected V binding;
    protected VM viewModel;
    protected Context mContext;
    protected boolean isInit = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam(savedInstanceState);
        viewModel = getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getContentView(), container, false);
        isCanLoadData(savedInstanceState);

        return binding.getRoot();
    }

    protected void isCanLoadData(Bundle savedInstanceState) {
        if (getUserVisibleHint() && !isInit) {
            isInit = true;
            initData(savedInstanceState);
            initView();
            initEvent();
        }
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initEvent();

    protected abstract void initView();

    public abstract void initParam(Bundle savedInstanceState);

    protected abstract int getContentView();

    protected abstract VM getViewModel();
}
