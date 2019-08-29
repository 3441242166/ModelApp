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
    private var end = 0
    private var begin = 0

    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var diffVertical = 0
    private var itemHeight = 0f

    private var textSizeA = 0f
    private var textSizeB = 0f
    private var textSizeC = 0f
    private var textSizeDiff = 0f

    var isCycle = true

    init {
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = Color.BLACK
    }

    var loadMore: () -> List<String> = { emptyList() }
        set(value) {
            isCycle = false
            field = value
            while (data.size < begin + 5) {
                val newData = field()
                if (newData.isEmpty()) {
                    break
                }
                data.addAll(newData)
            }
            logi(TAG, "begin = $begin  end = $end")
            invalidate()
        }

    var loadPreMore: () -> List<String> = { emptyList() }
        set(value) {
            isCycle = false
            field = value
            while (begin < 3) {
                val newData = field()
                if (newData.isNotEmpty()) {
                    data.addAll(0, newData)
                    begin += newData.size
                    end += newData.size
                } else {
                    break
                }
            }
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        textSizeA = heightSize / 8f
        textSizeB = textSizeA / 10 * 7
        textSizeC = textSizeA / 10 * 4
        textSizeDiff = textSizeB - textSizeC

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

            if (diffVertical > 0) {
                textPaint.textSize = textSizeC + textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(-2), 0f, -itemHeight * 2, textPaint)

                textPaint.textSize = textSizeB + textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(-1), 0f, -itemHeight, textPaint)

                textPaint.textSize = textSizeA - textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(0), 0f, 0f, textPaint)

                textPaint.textSize = textSizeB - textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(1), 0f, itemHeight, textPaint)

                textPaint.textSize = textSizeC - textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(2), 0f, itemHeight * 2, textPaint)
            } else {

                textPaint.textSize = textSizeC - textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(-2), 0f, -itemHeight * 2, textPaint)

                textPaint.textSize = textSizeB - textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(-1), 0f, -itemHeight, textPaint)

                textPaint.textSize = textSizeA - textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(0), 0f, 0f, textPaint)

                textPaint.textSize = textSizeB + textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(1), 0f, itemHeight, textPaint)

                textPaint.textSize = textSizeC + textSizeDiff * abs(diffVertical) / itemHeight
                drawText(getStringIndex(2), 0f, itemHeight * 2, textPaint)
            }

        }
    }

    private fun getStringIndex(num: Int): String {
        var index = begin + num
        if (!isCycle && (index >= data.size || index < 0)) {
            return ""
        }

        return when {
            index >= data.size -> data[begin + num - data.size]
            index < 0 -> data[data.size + index]
            else -> data[begin + num]
        }
    }

    private var lastDownY = 0
    private var lastClickTime = System.currentTimeMillis()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                // 防止连击出现的异常状况
                if (System.currentTimeMillis() - lastClickTime < 500) {
                    lastClickTime = System.currentTimeMillis()
                    return true
                }
                lastClickTime = System.currentTimeMillis()
                lastDownY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val move = event.y - lastDownY
                lastDownY = event.y.toInt()

                //logi(TAG,move.toString())
                // 数据有范围 到达边界防止继续滑动
                if (!isCycle && ((begin <= 0 && move > 0 && diffVertical >= 0) ||
                                (end >= data.size + (end - begin - 1) && move < 0))) {
                    if (begin < 0) {
                        begin = 0
                    }
                    if (end > data.size + 4) {
                        end = data.size + 4
                    }
                    diffVertical = 0
                    return true
                }

                moveAction(move.toInt())
            }
            MotionEvent.ACTION_UP -> {
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

    // 自动归位
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

    // 滑动一段距离后调整
    private fun moveAction(move: Int) {
        val lastDiff = diffVertical

        diffVertical += move
        diffVertical %= itemHeight.toInt()

        if (move > 0 && lastDiff > diffVertical) {
            end = adjustIndex(end, false)
            begin = adjustIndex(begin, false)
        } else if (move < 0 && lastDiff < diffVertical) {
            end = adjustIndex(end, true)
            begin = adjustIndex(begin, true)
        }
        invalidate()
    }

    // 滑动一个itemHeight后 调整数组下标
    private fun adjustIndex(num: Int, isAdd: Boolean): Int {
        logi(TAG, "num : $num    isAdd : $isAdd")

        if (!isCycle) {
            if (isAdd && num + 1 >= data.size) {
                data.addAll(loadMore())
                return data.size
            }
            if (!isAdd && num - 1 <= 0) {
                val newData = loadPreMore()
                data.addAll(0, newData)
                end += newData.size
                return 0
            }
        }

        return if (isAdd) {
            if (num + 1 >= data.size) 0 else num + 1
        } else {
            if (num - 1 < 0 && isCycle) data.size - 1 else num - 1
        }
    }

    fun needCycle(needCycle: Boolean) {
        isCycle = needCycle
        loadMore = { emptyList() }
        loadMore = { emptyList() }
    }

    fun setData(list: List<String>) {
        data.clear()
        data.addAll(list)
        begin = 0
        end = 5.coerceAtLeast(data.size)

//        if (end in 1..4) {
//            val newData = loadMore()
//            if (newData.isEmpty()) {
//                if (isCycle) {
//                    data.addAll(0, data)
//                    data.addAll(data)
//                }
//            } else {
//                while (begin + 5 < data.size) {
//                    data.addAll(newData)
//                    end += newData.size
//                }
//            }
//        }

        invalidate()
    }

    fun getSelectPostion() = begin

    fun getSelectString() = data[begin]
}