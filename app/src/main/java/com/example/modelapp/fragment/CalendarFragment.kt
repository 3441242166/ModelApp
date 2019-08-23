package com.example.modelapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.Gravity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.modelapp.R
import com.example.modelapp.activity.CreateScheduleActivity
import com.example.modelapp.util.logi
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlin.math.log

class CalendarFragment : BaseFragment<CalendarFgViewModel>() {


    @SuppressLint("RtlHardcoded")
    override fun initEvent() {

        menu_btn.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        switch_btn.setOnClickListener {

        }

        fab.setOnClickListener {
            startActivity(Intent(context, CreateScheduleActivity::class.java))
        }

        viewModel.date.observe(this, Observer {

        })
    }

    override fun getViewModel() = ViewModelProviders.of(this).get(CalendarFgViewModel::class.java)

    override fun getContentView() = R.layout.fragment_calendar
}

class CalendarFgViewModel : BaseViewModel() {
    public val date: MutableLiveData<String> = MutableLiveData()

}
