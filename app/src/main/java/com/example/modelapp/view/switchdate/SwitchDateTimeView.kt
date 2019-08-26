package com.example.modelapp.view.switchdate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.modelapp.R

class SwitchDateTimeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

//    private var leftIcon: Int
//    private var rightIcon: Int
//    private var itemContent: String?
//    private var leftTitle: String?
//    private var rightTitle: String?


    var startClick: (v: View) -> Unit = {}
    var endClick: (v: View) -> Unit = {}
    var typeClick: (v: View) -> Unit = {}


    init {
//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchDateTimeView)
//        itemContent = typedArray.getString(R.styleable.MyToolbar_content)
//        leftTitle = typedArray.getString(R.styleable.MyToolbar_left_title)
//        rightTitle = typedArray.getString(R.styleable.MyToolbar_right_title)
//        leftIcon = typedArray.getResourceId(R.styleable.MyToolbar_left_icon, -1)
//        rightIcon = typedArray.getResourceId(R.styleable.MyToolbar_right_icon, -1)
//        typedArray.recycle()

        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.view_switch_date, this)



    }

}