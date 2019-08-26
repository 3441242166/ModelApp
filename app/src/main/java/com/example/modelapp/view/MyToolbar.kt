package com.example.modelapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.modelapp.R
import kotlinx.android.synthetic.main.view_image_text.view.btLeftImage
import kotlinx.android.synthetic.main.view_image_text.view.btRightImage
import kotlinx.android.synthetic.main.view_image_text.view.content
import kotlinx.android.synthetic.main.view_my_toolbar.view.*

class MyToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var leftIcon: Int
    private var rightIcon: Int
    private var itemContent: String?
    private var leftTitle: String?
    private var rightTitle: String?


    var leftClick: (v: View) -> Unit = {}
    var rightClick: (v: View) -> Unit = {}
    var titleClick: (v: View) -> Unit = {}


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyToolbar)
        itemContent = typedArray.getString(R.styleable.MyToolbar_content)
        leftTitle = typedArray.getString(R.styleable.MyToolbar_left_title)
        rightTitle = typedArray.getString(R.styleable.MyToolbar_right_title)
        leftIcon = typedArray.getResourceId(R.styleable.MyToolbar_left_icon, -1)
        rightIcon = typedArray.getResourceId(R.styleable.MyToolbar_right_icon, -1)
        typedArray.recycle()

        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.view_my_toolbar, this)

        content.text = itemContent


        leftTitle?.run {
            btLeftTitle.text = this
            btLeftTitle.visibility = View.VISIBLE
        }

        rightTitle?.run {
            btRightTitle.text = this
            btRightTitle.visibility = View.VISIBLE
        }


        if (leftIcon != -1) {
            btLeftImage.setImageResource(leftIcon)
            btLeftImage.visibility = View.VISIBLE
            btLeftTitle.visibility = View.GONE
        }

        if (rightIcon != -1) {
            btRightImage.setImageResource(rightIcon)
            btRightImage.visibility = View.VISIBLE
            btRightTitle.visibility = View.GONE
        }

        btLeftImage.setOnClickListener { leftClick(this) }
        btRightImage.setOnClickListener { rightClick(this) }
        content.setOnClickListener { titleClick(this) }

    }

}