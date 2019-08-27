package com.example.modelapp.view.switchdate

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.modelapp.util.logi
import kotlin.math.abs


const val TAG = "RollerView"

class RollerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val data = mutableListOf<String>()
    private val slidingWindow = mutableListOf<String>()
    private var end = 0
    private var begin = 0

    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val mSelectedItemTextSize = 40f
    private val mTextSize = 30f
    private var diffVertical = 0
    private var itemHeight = 0f

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
        if (data.size == 0) {
            return
        }

        canvas?.run {
            translate((width / 2).toFloat(), (height / 2).toFloat() + diffVertical)

            itemHeight = height / 3f

            drawText(slidingWindow[0], 0f, -itemHeight * 2, textPaint)
            drawText(slidingWindow[1], 0f, -itemHeight, textPaint)
            drawText(slidingWindow[2], 0f, 0f, textPaint)
            drawText(slidingWindow[3], 0f, itemHeight, textPaint)
            drawText(slidingWindow[4], 0f, itemHeight * 2, textPaint)

            // 设置字体大小 字体颜色 字体透明度

            // 画矩形蒙版


        }
    }

    private var lastDownY = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {

            MotionEvent.ACTION_DOWN -> lastDownY = event.y.toInt()
            MotionEvent.ACTION_MOVE -> {
                val move = event.y - lastDownY
                lastDownY = event.y.toInt()
                moveAction(move.toInt())
            }
            MotionEvent.ACTION_UP -> {
                logi(TAG, diffVertical.toString())
                if (abs(diffVertical) > (itemHeight / 2)) {
                    if (diffVertical > 0)
                        returnCenter(-(itemHeight - diffVertical).toInt())
                    else
                        returnCenter((itemHeight + diffVertical).toInt())
                } else {
                    returnCenter(diffVertical)
                }
            }
        }
        return true
    }

    private fun returnCenter(start: Int) {
        val valAnimation = ValueAnimator.ofInt(start, 0)
        lastDownY = start
        valAnimation.addUpdateListener {
            val move = it.animatedValue as Int - lastDownY
            lastDownY = it.animatedValue as Int
            moveAction(move)
        }
        valAnimation.start()
    }

    private fun moveAction(move: Int) {
        val lastDiff = diffVertical

        diffVertical += move
        diffVertical %= itemHeight.toInt()

        if (move > 0 && lastDiff > diffVertical) {
            slidingWindow.removeAt(slidingWindow.size - 1)
            slidingWindow.add(0, data[begin])

            end = adjustIndex(end, false)
            begin = adjustIndex(begin, false)
        } else if (move < 0 && lastDiff < diffVertical) {
            slidingWindow.removeAt(0)
            slidingWindow.add(data[end])

            end = adjustIndex(end, true)
            begin = adjustIndex(begin, true)
        }
        invalidate()
    }

    private fun adjustIndex(num: Int, isAdd: Boolean): Int {
        return if (isAdd) {
            if (num + 1 >= data.size) 0 else num + 1
        } else {
            if (num - 1 < 0) data.size - 1 else num - 1
        }
    }

    fun setData(list: List<String>) {
        data.clear()
        slidingWindow.clear()
        data.addAll(list)
        for (i in 0..4) {
            slidingWindow.add(data[i])
        }
        begin = data.size - 1
        end = 5
        requestLayout()
    }

}