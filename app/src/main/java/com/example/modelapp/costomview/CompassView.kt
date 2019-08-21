package com.example.modelapp.costomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.abs

const val TAG = "CompassView"

class CompassView : View , SensorEventListener {


    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dircationTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)


    private var outRadius = 0f
    private var inRadius = 0f
    private var difRadius = 0f

    private lateinit var sensorManager: SensorManager
    private var acc_sensor: Sensor? = null
    private var mag_sensor: Sensor? = null
    //加速度传感器数据
    private var accValues = FloatArray(3)
    //地磁传感器数据
    private var magValues = FloatArray(3)
    //旋转矩阵，用来保存磁场和加速度的数据
    private var r = FloatArray(9)
    //模拟方向传感器的数据（原始数据为弧度）
    private var values = FloatArray(3)

    var angle = 0f

    init {
        textPaint.run {
            color = Color.parseColor("#F1F1F1")
            textAlign = Paint.Align.CENTER
        }
        dircationTextPaint.run {
            color = Color.parseColor("#F1F1F1")
            textAlign = Paint.Align.CENTER
        }
        linePaint.run {
            color = Color.parseColor("#F1F1F1")
            style = Paint.Style.FILL
        }


    }

    constructor(context: Context) :super(context) {
        initSensor()
    }

    constructor(context: Context, attrs:AttributeSet) :super(context,attrs) {
        initSensor()
    }

    private fun initSensor() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mag_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        // 注册监听：
        sensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        Log.i(TAG, "widthMode = ${onMeasureName(widthMode)}  widthSize = $widthSize  heightMode = ${onMeasureName(heightMode)}  heightSize = $heightSize")

        heightSize = widthSize

        outRadius = widthSize / 5 * 4f / 2
        inRadius = widthSize / 20 * 15f / 2
        difRadius = outRadius - inRadius
        Log.i(TAG, "outRadius = $outRadius  inRadius = $inRadius  difRadius = $difRadius")

        textPaint.textSize = difRadius / 2 * 3
        dircationTextPaint.textSize = difRadius * 3

        setMeasuredDimension(
                resolveSize(widthSize, widthMeasureSpec),
                resolveSize(heightSize, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.run {
            translate((width / 2).toFloat(), (height / 2).toFloat())
            linePaint.color = Color.RED
            linePaint.textSize = dircationTextPaint.textSize
            drawLine(0f, -(inRadius - difRadius), 0f, -outRadius, linePaint)
            linePaint.color = textPaint.color
            linePaint.textSize = textPaint.textSize

            rotate(-angle + 180)

            for (i in 0..360 step 2) {
                if (i % 30 == 0 && i != 360) {
                    drawLine(0f, inRadius - difRadius, 0f, outRadius, linePaint)
                    drawText(i.toString(), 0f, outRadius + textPaint.textSize, textPaint)
                    val text = when (i) {
                        0 -> "北"
                        90 -> "东"
                        180 -> "南"
                        270 -> "西"
                        else -> ""
                    }
                    if (text.isNotEmpty()) {
                        drawText(text, 0f, outRadius - dircationTextPaint.textSize, dircationTextPaint)
                    }
                } else {
                    drawLine(0f, inRadius, 0f, outRadius, linePaint)
                }
                rotate(2f)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.run {
            if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
                accValues = values.clone()
            } else if (sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                magValues = values.clone()
            }

            SensorManager.getRotationMatrix(r, null, accValues, magValues)
            SensorManager.getOrientation(r, values)

            val newAngle = Math.toDegrees(values[0].toDouble()).toFloat()

            if (abs(newAngle - angle) > 2) {
                angle = newAngle
                invalidate()
            }
        }
    }
}


fun onMeasureName(num: Int): String {

    when (num) {
        View.MeasureSpec.UNSPECIFIED -> {
            return "UNSPECIFIED"
        }
        View.MeasureSpec.EXACTLY -> {
            return "EXACTLY"
        }
        View.MeasureSpec.AT_MOST -> {
            return "AT_MOST"
        }
    }

    return ""
}