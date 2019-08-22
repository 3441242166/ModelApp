package com.example.modelapp.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.modelapp.R
import com.example.modelapp.model.MonthFgModel
import com.example.mvvm.base.BaseFragment

class MonthFragment : BaseFragment<MonthFgModel>() {

    override fun getContentView() = R.layout.activity_main

    override fun getViewModel() = ViewModelProviders.of(this).get(MonthFgModel::class.java)

    override fun initParam(savedInstanceState: Bundle?) {

    }

}