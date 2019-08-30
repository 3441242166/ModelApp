package com.example.modelapp.util

import android.util.Log
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.base.BaseViewModel


fun Any.logi( msg: String) {
    Log.i(javaClass.name, msg)
}

fun Any.loge(tag: String, msg: String) {
    Log.e(tag, msg)
}

fun Any.logw(tag: String, msg: String) {
    Log.w(tag, msg)
}

fun Any.logd(tag: String, msg: String) {
    Log.d(tag, msg)
}

fun Any.logv(tag: String, msg: String) {
    Log.v(tag, msg)
}
