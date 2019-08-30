package com.example.modelapp.view.switchdate

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.modelapp.R
import com.example.modelapp.util.logi
import com.example.mvvm.helper.*
import kotlinx.android.synthetic.main.view_switch_date.view.*
import java.util.*


class SwitchDateTimeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    //    private var leftIcon: Int
//    private var rightIcon: Int
//    private var itemContent: String?
//    private var leftTitle: String?
//    private var rightTitle: String?
    companion object {
        val WEEKS = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val HOURS = listOf("01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24")
        val TIMES = listOf("00", "05", "10", "15", "20",
                "25", "30", "35", "40", "45", "50", "55")

        const val LOAD_NUM = 10
    }

    private var beginMonthDay = -1
    private var endMonthDay = 1
    private var beginYear = -1
    private var endYear = 1

    var startClick: (v: View) -> Unit = {}
    var endClick: (v: View) -> Unit = {}
    var typeClick: (isCheck: Boolean) -> Unit = {}

    private val date = Date()
    private val year = 2018

    init {
        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.view_switch_date, this)

        dateRoller.setData(listOf(getDateString(date, DateType.MONTH_DAY.str) + getWeek(date)))
        dateRoller.needCycle(false)
        hourRoller.setData(HOURS)
        timeRoller.setData(TIMES)

        yearRoller.setData(listOf("${year}年"))
        yearRoller.needCycle(false)
        monthRoller.setData(listOf("1月", "2月", "3月", "4月", "5月", "6月",
                "7月", "8月", "9月", "10月", "11月", "12月"))
        monthRoller.needCycle(false)
        dayRoller.setData(getDays(yearRoller.getSelectString(), monthRoller.getSelectString()))


        dateRoller.loadMore = {
            val newData = mutableListOf<String>()
            for (i in 1..LOAD_NUM) {
                newData.add(getAddDayString(date, endMonthDay, DateType.MONTH_DAY) + getWeek(getAddDayDate(date, endMonthDay++)))
            }
            newData
        }

        dateRoller.loadPreMore = {
            val newData = mutableListOf<String>()
            for (i in 1..LOAD_NUM) {
                newData.add(0, getAddDayString(date, beginMonthDay, DateType.MONTH_DAY) + getWeek(getAddDayDate(date, beginMonthDay--)))
            }
            newData
        }

        yearRoller.loadMore = {
            val newData = mutableListOf<String>()
            for (i in 1..LOAD_NUM) {
                newData.add("${year + endYear++}年")
            }
            newData
        }

        yearRoller.loadPreMore = {
            val newData = mutableListOf<String>()
            for (i in 1..LOAD_NUM) {
                newData.add(0, "${year + beginYear--}年")
            }
            newData
        }

        yearRoller.selectListener = {
            dayRoller.setData(getDays(it, monthRoller.getSelectString()))
        }

        monthRoller.selectListener = {
            dayRoller.setData(getDays(yearRoller.getSelectString(), it))
        }

        btBegin.setOnClickListener {
            btBegin.setTextColor(Color.parseColor("#2E7AFD"))
            btEnd.setTextColor(Color.parseColor("#999999"))

            startClick(it)
        }

        btEnd.setOnClickListener {
            btBegin.setTextColor(Color.parseColor("#999999"))
            btEnd.setTextColor(Color.parseColor("#2E7AFD"))

            endClick(it)
        }

        btSwitch.setOnCheckedChangeListener { _, _ ->
            typeClick(btSwitch.isChecked)
            if (btSwitch.isChecked) {
                allDayLayout.visibility = View.VISIBLE
                dateTimeLayout.visibility = View.GONE
            } else {
                allDayLayout.visibility = View.GONE
                dateTimeLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun getWeek(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        var weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1
        if (weekIndex < 0) {
            weekIndex = 0
        }
        return WEEKS[weekIndex]
    }

    private fun getDays(year: String, month: String): List<String> {
        val result = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        calendar.time = getDateByDateString("$year$month", "yyyy年MM月")
        logi(calendar.time.toString())
        val count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..count) {
            result.add("$i 日")
        }
        return result
    }
}