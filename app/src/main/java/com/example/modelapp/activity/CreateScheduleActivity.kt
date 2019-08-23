package com.example.modelapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.modelapp.R
import com.example.modelapp.model.MainActivityModel
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseViewModel

class CreateScheduleActivity : BaseActivity<CreateScheduleModel>() {


    override fun getViewModel() = ViewModelProviders.of(this).get(CreateScheduleModel::class.java)

    override fun getContentView() = R.layout.activity_create_schedule
}

class CreateScheduleModel : BaseViewModel(){

}
