package com.example.modelapp.model

import androidx.lifecycle.MutableLiveData
import androidx.fragment.app.Fragment

import com.example.mvvm.base.BaseViewModel

class MainActivityModel : BaseViewModel() {

    public val date: MutableLiveData<String> = MutableLiveData()

}
