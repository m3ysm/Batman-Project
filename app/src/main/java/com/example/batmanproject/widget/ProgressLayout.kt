package com.example.batmanproject.widget

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.batmanproject.R
import kotlinx.android.synthetic.main.layout_progress.view.*

class ProgressLayout : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        init()
    }

    private fun init() {
        inflate(context, R.layout.layout_progress, this)
        setStatus(STATUS.DONE)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val wrapDrawable = DrawableCompat.wrap(progressBar.indeterminateDrawable)
            DrawableCompat.setTint(
                wrapDrawable,
                ContextCompat.getColor(context, R.color.orange)
            )
            progressBar.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        } else {
            progressBar.indeterminateDrawable.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.orange
                ), PorterDuff.Mode.SRC_IN
            )
        }
    }

    fun setStatus(status: STATUS, errorText: String = "") {
        when (status) {
            // error
            STATUS.ERROR -> {
                prpgressBarRootLayout.visibility = View.VISIBLE
                progressTextView.text = "بروز خطا"
                progressTextView.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                prpgressBarRootLayout.visibility = View.VISIBLE
                progressTextView.text = errorText
            }

            // loading
            STATUS.LOADING -> {
                prpgressBarRootLayout.visibility = View.VISIBLE
                progressTextView.text = "لطفا منتظر بمانید"
                progressTextView.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                prpgressBarRootLayout.visibility = View.VISIBLE
                requestLayout()
            }

            // done
            STATUS.DONE -> {
                prpgressBarRootLayout.visibility = View.GONE
                progressTextView.text = ""
                progressTextView.visibility = View.INVISIBLE
                progressBar.visibility = View.INVISIBLE
                prpgressBarRootLayout.visibility = View.GONE
            }
        }
    }

    fun setErrorText(text: String) {
        progressTextView.text = text
    }

    enum class STATUS {
        LOADING,
        ERROR,
        DONE
    }
}
