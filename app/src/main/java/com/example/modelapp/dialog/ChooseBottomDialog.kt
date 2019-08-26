package com.example.modelapp.dialog


import android.content.Context
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
        btExit.setOnClickListener {
            cancel()
        }
    }

    private fun initView() {

    }

}
