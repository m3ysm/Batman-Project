package com.example.batmanproject.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.example.batmanproject.R
import kotlinx.android.synthetic.main.layout_toolbar_main.view.*

class MainToolbar : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.layout_toolbar_main, this)
    }

    fun setTitle(title: String) {
        titleText.text = title
    }

    fun setOnBackPressedListener(listener: OnClickListener) {
        backImageView.setOnClickListener(listener)
    }
}