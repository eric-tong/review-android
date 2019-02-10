package com.aiimpactweekend.review.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.aiimpactweekend.review.R

class TagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    init {
        inflate(getContext(), R.layout.view_tag, this)
    }
}