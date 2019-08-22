package com.example.mvvm.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle

import com.example.mvvm.R


abstract class BaseScreenDialog(context: Context) : Dialog(context, R.style.style_screen_dialog) {

    protected abstract val contentView: Int

    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        init()
    }
}
