package com.example.mvvm.base;

import android.content.Context;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    public final String TAG = getClass().getSimpleName();

    protected VM viewModel;
    protected Context mContext;
    protected boolean isInitData = false;
    protected boolean isInitView = false;

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
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        isCanLoadData(savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && !isInitView) {
            isInitView = true;
            initView();
            initEvent();
        }
    }

    protected void isCanLoadData(Bundle savedInstanceState) {
        if (!isInitData) {
            isInitData = true;
            initData(savedInstanceState);

        }
    }

    protected void initData(Bundle savedInstanceState) {

    }

    protected void initEvent() {

    }

    protected void initView() {

    }

    public void initParam(Bundle savedInstanceState) {

    }

    protected abstract int getContentView();

    protected abstract VM getViewModel();
}
