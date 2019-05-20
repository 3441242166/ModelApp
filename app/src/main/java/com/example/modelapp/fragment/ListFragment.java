package com.example.modelapp.fragment;

import android.arch.lifecycle.ViewModelProviders;

import com.example.modelapp.R;
import com.example.modelapp.databinding.FragmentListBinding;
import com.example.modelapp.model.ListFragmentModel;
import com.example.mvvm.base.BaseFragment;

public class ListFragment extends BaseFragment<FragmentListBinding, ListFragmentModel> {
    @Override
    protected int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    protected ListFragmentModel getViewModel() {
        return ViewModelProviders.of(this).get(ListFragmentModel.class);
    }
}
