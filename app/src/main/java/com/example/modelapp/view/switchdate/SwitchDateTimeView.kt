package com.example.modelapp.view.switchdate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.modelapp.R
import kotlinx.android.synthetic.main.activity_create_schedule.view.*
import kotlinx.android.synthetic.main.view_switch_date.view.*

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

        dateRoller.setData(listOf("10月03日", "10月04日", "10月05日", "10月06日", "10月07日",
                "10月08日", "10月09日", "10月10日", "10月11日", "10月12日"))
        //dateRoller.setData(listOf("asd","qwe","zxc"))
        dateRoller.needCycle(false)
        hourRoller.setData(listOf("01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24"))
        timeRoller.setData(listOf("00", "05", "10", "15", "20",
                "25", "30", "35", "40", "45", "50", "55"))

        yearRoller.setData(listOf("10月03日", "10月04日", "10月05日", "10月06日", "10月07日",
                "10月08日", "10月09日", "10月10日", "10月11日", "10月12日"))
        yearRoller.needCycle(false)
        monthRoller.setData(listOf("1月", "2月", "3月", "4月", "5月", "6月",
                "7月", "8月", "9月", "10月", "11月", "12月"))
        dayRoller.setData(listOf("00", "05", "10", "15", "20",
                "25", "30", "35", "40", "45", "50", "55"))
        dayRoller.needCycle(false)

//        dateRoller.loadMore = {
//            listOf("xxx")
//        }
//
//        dateRoller.loadPreMore = {
//            listOf("yyy")
//        }

        btBegin.setOnClickListener {
            startClick(it)
        }

        btEnd.setOnClickListener {
            endClick(it)
        }

        btSwitch.setOnCheckedChangeListener { _, _ ->
            if (btSwitch.isChecked) {
                allDayLayout.visibility = View.VISIBLE
                dateTimeLayout.visibility = View.GONE
            } else {
                allDayLayout.visibility = View.GONE
                dateTimeLayout.visibility = View.VISIBLE
            }
        }
    }

}