package com.example.modelapp.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.app.Fragment;

import com.example.modelapp.fragment.FourFragment;
import com.example.modelapp.fragment.OneFragment;
import com.example.modelapp.fragment.ThreeFragment;
import com.example.modelapp.fragment.TwoFragment;
import com.example.mvvm.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityModel extends BaseViewModel {

    private MutableLiveData<List<Fragment>> fragments;

    public List<Fragment> getFragments(){
        if (fragments == null) {
            fragments = new MutableLiveData<>();
            List<Fragment> list = new ArrayList<>(4);
            list.add(new OneFragment());
            list.add(new TwoFragment());
            list.add(new ThreeFragment());
            list.add(new FourFragment());
            fragments.setValue(list);
        }
        return fragments.getValue();
    }
}
