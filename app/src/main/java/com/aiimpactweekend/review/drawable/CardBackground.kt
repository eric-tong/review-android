package com.aiimpactweekend.review.drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.support.v4.content.ContextCompat
import com.aiimpactweekend.review.R

class CardBackground(context: Context) : Shape() {
    private val radius = context.resources.getDimension(R.dimen.card_radius)
    private val shadowRadius = context.resources.getDimension(R.dimen.card_shadow_radius)
    private val padding = context.resources.getDimension(R.dimen.card_padding)
    private val shadowColor = ContextCompat.getColor(context, R.color.card_shadow)
    private val shadowDisplacement = context.resources.getDimension(R.dimen.card_shadow_displacement)
    
    override fun draw(canvas: Canvas, paint: Paint) {
        val bounds = getCanvasBounds(canvas)
        paint.setWhiteWithShadow()
        canvas.drawCardBackground(bounds, paint)
    }

    private fun getCanvasBounds(canvas: Canvas): Rect {
        val bounds = Rect()
        canvas.getClipBounds(bounds)
        return bounds
    }

    private fun Paint.setWhiteWithShadow() {
        color = Color.WHITE
        setShadowLayer(
                shadowRadius,
                0F,
                shadowDisplacement,
                shadowColor
        )
    }

    private fun Canvas.drawCardBackground(bounds: Rect, paint: Paint) {
        drawRoundRect(
                (bounds.left + padding),
                (bounds.top + padding),
                (bounds.right - padding),
                (bounds.bottom - padding),
                radius,
                radius,
                paint
        )
    }

    fun toDrawable(): Drawable {
        return ShapeDrawable(this)
    }
}