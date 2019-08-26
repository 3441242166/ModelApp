package com.example.modelapp.view.switchdate

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View


const val TAG = "RollerView"

class RollerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val data = mutableListOf<String>()
    private var recycled = true
    private var position = 0

    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val mSelectedItemTextSize = 40f
    private val mTextSize = 30f
    private var diffVertical = 0

    init {
        textPaint.textSize = 28f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(
                resolveSize(widthSize, widthMeasureSpec),
                resolveSize(heightSize, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.run {
            translate((width / 2).toFloat(), (height / 2).toFloat() + diffVertical)
            drawText("8月30日", 0f, height / 3f, textPaint)
            drawText("8月29日", 0f, 0f, textPaint)
            drawText("8月28日", 0f, -height / 3f, textPaint)

        }
    }

    fun getString(pos: Int): String {
        if (pos < 0) {
            return data[data.size + (pos % data.size)]
        }
        if (pos > data.size) {
            return data[pos % data.size]
        }
        return data[pos]
    }

    fun setData(list: List<String>) {
        data.clear()
        data.addAll(list)
        requestLayout()
    }

    fun setRecycled(recycled: Boolean) {
        this.recycled = recycled
    }

}