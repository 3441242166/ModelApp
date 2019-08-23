package com.example.modelapp.activity


import android.os.Build
import androidx.lifecycle.ViewModelProviders

import com.example.modelapp.R
import com.example.modelapp.model.MainActivityModel
import com.example.mvvm.base.BaseActivity
import android.view.View
import androidx.lifecycle.Observer
import com.example.modelapp.fragment.CalendarFragment


class MainActivity : BaseActivity<MainActivityModel>() {


    override fun getViewModel() = ViewModelProviders.of(this).get(MainActivityModel::class.java)

    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment, CalendarFragment(), "calendar_fragment")
                .commit()
    }

    override fun initEvent() {

    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

}
