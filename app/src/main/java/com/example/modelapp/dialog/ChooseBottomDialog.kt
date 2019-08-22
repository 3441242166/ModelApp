package com.example.modelapp.dialog


import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.modelapp.R
import com.example.mvvm.base.BaseScreenDialog
import kotlinx.android.synthetic.main.dialog_chooes_type.*


class ChooseBottomDialog(context: Context) : BaseScreenDialog(context) {


    var onChooseListener = {}

    override val contentView = R.layout.dialog_chooes_type

    override fun init() {
        initView()
        initEvent()
    }

    private fun initEvent() {
        exit.setOnClickListener {
            cancel()
        }
    }

    private fun initView() {

    }

}
