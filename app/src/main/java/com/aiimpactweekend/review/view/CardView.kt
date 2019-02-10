package com.aiimpactweekend.review.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView
import com.aiimpactweekend.review.R
import com.aiimpactweekend.review.drawable.CardBackground


class CardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = R.style.CardTextView
) : TextView(context, attrs, defStyle, defStyleRes) {
    init {
        background = cardBackground
    }

    private val cardBackground: Drawable
        get() {
            val shadowRadius = context.resources.getDimension(R.dimen.card_shadow_radius)
            val padding = context.resources.getDimension(R.dimen.card_padding)
            val paddingTop = shadowRadius - (padding - shadowRadius)

            setPadding(
                padding.toInt(),
                paddingTop.toInt(),
                padding.toInt(),
                padding.toInt()
            )

            return CardBackground(context.applicationContext).toDrawable()
        }
}