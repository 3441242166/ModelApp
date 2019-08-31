package com.example.modelapp.view.switchdate

import android.animation.Animator
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
import kotlin.math.max
import kotlin.math.min


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
            logi("setLoadMore")
            isCycle = false
            field = value

            while (data.size < begin + 5) {
                val newData = field()
                if (newData.isEmpty()) {
                    break
                }
                data.addAll(newData)
            }
            invalidate()
        }

    var loadPreMore: () -> List<String> = { emptyList() }
        set(value) {
            logi("setLoadPreMore")
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

    var selectListener: (String) -> Unit = {}

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

                // 数据有范围 到达边界防止继续滑动
                if (!isCycle && ((begin <= 0 && move > 0 && diffVertical >= 0) ||
                                (end >= data.size + (end - begin - 1) && move < 0))) {
                    logi("begin = $begin  end = $end  diff = $diffVertical  move = $move")
                    if (begin < 0) {
                        begin = 0
                    }
                    if (end > data.size + (end - begin - 1)) {
                        end = data.size + (end - begin - 1)
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
        valAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                selectListener(data[begin])
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
        valAnimation.start()
    }

    // 滑动一段距离后调整
    private fun moveAction(move: Int) {
        val lastDiff = diffVertical

        if (itemHeight.toInt() == 0) {
            return
        }
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
        if (!isCycle) {
            if (isAdd && num + 1 >= data.size) {
                data.addAll(loadMore())
                return data.size
            }
            if (!isAdd && num - 3 <= 0) {
                val newData = loadPreMore()
                data.addAll(0, newData)
                end += newData.size
                begin += newData.size
                return begin - 1
            }
        }

        return if (isAdd) {
            if (num + 1 >= data.size) 0 else num + 1
        } else {
            if (num - 1 < 0 && isCycle) data.size - 1 else num - 1
        }
    }

    fun setData(list: List<String>, needCycle: Boolean = true) {
        isCycle = needCycle

        data.clear()
        data.addAll(list)
        begin = 0
        end = min(data.size, 5)

        if (end < 5) {
            if (!isCycle) {
                loadMore = loadMore
                loadPreMore = loadPreMore
            }
        }

        invalidate()
    }

    fun getSelectPostion() = begin

    fun getSelectString() = data[begin]
}