package com.example.modelapp.fragment;

import android.arch.lifecycle.ViewModelProviders;

import com.example.modelapp.R;
import com.example.modelapp.databinding.FragmentListBinding;
import com.example.modelapp.databinding.FragmentTwoBinding;
import com.example.modelapp.model.ListFragmentModel;
import com.example.mvvm.base.BaseFragment;

public class TwoFragment  extends BaseFragment<FragmentTwoBinding, ListFragmentModel> {
    @Override
    protected int getContentView() {
        return R.layout.fragment_two;
    }

    @Override
    protected ListFragmentModel getViewModel() {
        return ViewModelProviders.of(this).get(ListFragmentModel.class);
    }
}
