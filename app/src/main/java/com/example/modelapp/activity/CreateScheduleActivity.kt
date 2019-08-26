package com.example.modelapp.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.modelapp.R
import com.example.modelapp.dadabase.AppDataBase
import com.example.modelapp.model.MainActivityModel
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_create_schedule.*

class CreateScheduleActivity : BaseActivity<CreateScheduleModel>() {


    override fun getViewModel() = ViewModelProviders.of(this).get(CreateScheduleModel::class.java)

    override fun getContentView() = R.layout.activity_create_schedule

    override fun initEvent() {
        btLocation.setOnClickListener {
            startActivity(Intent(mContext, LocationActivity::class.java))
        }
    }
}

class CreateScheduleModel : BaseViewModel() {

    //private val scheduleDao = AppDataBase.getInstance().scheduleDao()

    fun insertSchedule() {

    }


}
