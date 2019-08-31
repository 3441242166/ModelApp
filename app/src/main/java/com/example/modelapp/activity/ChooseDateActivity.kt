package com.example.modelapp.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.modelapp.R
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_choose_date.*
import kotlinx.android.synthetic.main.layout_start_end_date.*

class ChooseDateActivity : BaseActivity<ChooseDateModel>() {

    private val endClick: (View) -> Unit = {
        startSelect(false)
        chooseDateView.setDate(tvStartDate.text.toString(), tvStartTime.text.toString())
    }

    private val startClick: (View) -> Unit = {
        startSelect(true)
        chooseDateView.setDate(tvEndDate.text.toString(), tvEndTime.text.toString())

    }

    override fun getContentView() = R.layout.activity_choose_date

    override fun getViewModel() = ViewModelProviders.of(this).get(ChooseDateModel::class.java)

    override fun initView() {
        startSelect(true)
    }

    override fun initEvent() {
        chooseDateView.endClick = endClick

        chooseDateView.startClick = startClick

        btEndLayout.setOnClickListener { endClick(it) }

        btStartLayout.setOnClickListener { startClick(it) }

        chooseDateView.typeClick = { isAllDay ->
            if (isAllDay) {
                tvStartDate.text
                tvStartTime.visibility = View.GONE
                tvEndDate.text
                tvEndTime.visibility = View.GONE
            } else {
                tvStartDate.text
                tvStartTime.visibility = View.VISIBLE
                tvEndDate.text
                tvEndTime.visibility = View.VISIBLE
            }
        }
    }

    private fun startSelect(isSelect: Boolean) {
        chooseDateView.setStartSelect(isSelect)

        if (isSelect) {
            btStartLayout.setBackgroundColor(resources.getColor(R.color.date_selected))
            tvStartDate.setTextColor(resources.getColor(R.color.date_selected_text))
            tvStartTime.setTextColor(resources.getColor(R.color.date_selected_text))
            tvStartWeek.setTextColor(resources.getColor(R.color.date_selected_text))

            btEndLayout.setBackgroundColor(resources.getColor(R.color.date_not_select))
            tvEndDate.setTextColor(resources.getColor(R.color.date_not_select_text))
            tvEndTime.setTextColor(resources.getColor(R.color.date_not_select_text))
            tvEndWeek.setTextColor(resources.getColor(R.color.date_not_select_text))
        } else {
            btStartLayout.setBackgroundColor(resources.getColor(R.color.date_not_select))
            tvStartDate.setTextColor(resources.getColor(R.color.date_not_select_text))
            tvStartTime.setTextColor(resources.getColor(R.color.date_not_select_text))
            tvStartWeek.setTextColor(resources.getColor(R.color.date_not_select_text))

            btEndLayout.setBackgroundColor(resources.getColor(R.color.date_selected))
            tvEndDate.setTextColor(resources.getColor(R.color.date_selected_text))
            tvEndTime.setTextColor(resources.getColor(R.color.date_selected_text))
            tvEndWeek.setTextColor(resources.getColor(R.color.date_selected_text))
        }
    }

}

class ChooseDateModel : BaseViewModel() {

}
