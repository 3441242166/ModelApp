package com.example.modelapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.modelapp.R
import kotlinx.android.synthetic.main.view_image_text.view.*

class ImageTextItem @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val leftIcon: Int
    private val rightIcon: Int
    private val itemContent: String?

    //var onClick: (v: View) -> Unit = {}

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextItem)
        itemContent = typedArray.getString(R.styleable.ImageTextItem_content)
        leftIcon = typedArray.getResourceId(R.styleable.ImageTextItem_left_icon, -1)
        rightIcon = typedArray.getResourceId(R.styleable.ImageTextItem_right_icon, -1)
        typedArray.recycle()

        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.view_image_text, this)


        content.text = itemContent

        if (leftIcon != -1) {
            btLeftImage.setImageResource(leftIcon)
            btLeftImage.visibility = visibility
        }

        if (rightIcon != -1) {
            btRightImage.setImageResource(rightIcon)
            btRightImage.visibility = visibility
        }

        //setOnClickListener { onClick(this) }
    }

}
