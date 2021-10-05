package com.example.batmanproject.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.batmanproject.R
import com.example.batmanproject.databinding.LayoutToolbarMainBinding

class MainToolbar : RelativeLayout {

    private var _binding: LayoutToolbarMainBinding? = null
    private val binding get() = _binding!!
    private var title: String = ""

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    init {
        _binding = LayoutToolbarMainBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun init(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.mainToolbar_styleable, 0, 0).apply {
            title = getString(R.styleable.mainToolbar_styleable_mainToolbar_title).toString()
        }
        setTitle(title)
    }

    private fun setTitle(title: String) {
        binding.textViewMainToolbarTitle.text = title
    }

    fun setOnBackPressedListener(listener: OnClickListener) {
        binding.imageViewMainToolbarBack.setOnClickListener(listener)
    }
}