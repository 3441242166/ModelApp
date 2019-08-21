package com.example.modelapp.fragment

import android.arch.lifecycle.ViewModelProviders
import com.example.modelapp.R
import com.example.modelapp.databinding.FragmentMainBinding
import com.example.modelapp.model.ListFragmentModel
import com.example.mvvm.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding, ListFragmentModel>() {

    override fun getContentView() = R.layout.fragment_main

    override fun getViewModel(): ListFragmentModel = ViewModelProviders.of(this).get(ListFragmentModel::class.java)


}