package com.example.modelapp.fragment

import android.annotation.SuppressLint
import android.view.Gravity
import androidx.lifecycle.ViewModelProviders
import com.example.modelapp.R
import com.example.modelapp.util.logi
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlin.math.log

class CalendarFragment : BaseFragment<CalendarFgViewModel>() {


    @SuppressLint("RtlHardcoded")
    override fun initEvent() {
        logi("wanhao", "initEvent")

        menu_btn.setOnClickListener {
            logi(TAG, "menu_click")
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        switch_btn.setOnClickListener {

        }

        fab.setOnClickListener {
            logi(TAG, "fab_click")
        }
    }

    override fun getViewModel() = ViewModelProviders.of(this).get(CalendarFgViewModel::class.java)

    override fun getContentView() = R.layout.fragment_calendar
}

class CalendarFgViewModel : BaseViewModel() {

}
