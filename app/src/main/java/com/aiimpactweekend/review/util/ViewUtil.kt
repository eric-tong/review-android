package com.aiimpactweekend.review.util

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import com.aiimpactweekend.review.constants.DragState
import com.aiimpactweekend.review.constants.DragState.PICKED_UP
import com.aiimpactweekend.review.constants.POSITION_BOTTOM
import com.aiimpactweekend.review.constants.POSITION_TOP
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.hypot

val View.bounds: Rect
    get() {
        val center = IntArray(2)
        getLocationOnScreen(center)
        return Rect(
                center[0] + paddingLeft,
                center[1] + paddingTop,
                center[0] + width - paddingRight,
                center[1] + height - paddingBottom
        )
    }

fun View.isAdjacentTo(views: List<View>): Boolean {
    if (views.isEmpty()) return true
    views.forEach {
        if (bounds.intersect(it.bounds)) return true
    }
    return false
}

fun View.getPerpendicularDistanceToCorner(angleInDegs: Double): Double {
    val beta = atan(height.toDouble() / width.toDouble()) - Math.toRadians(angleInDegs)
    return hypot / 2 * cos(beta)
}

val View.hypot: Double
    get() {
        return hypot(width.toDouble(), height.toDouble())
    }

fun View.translateBy(dragX: Float, dragY: Float) {
    translationX += dragX
    translationY += dragY
}

fun View.getDragState(event: MotionEvent): DragState {
    return if (event.isAboveMiddle(this)) PICKED_UP(POSITION_TOP) else PICKED_UP(POSITION_BOTTOM)
}

fun View.reset(scale: Float) {
    animate().cancel()
    translationX = 0f
    translationY = 0f
    scaleX = scale
    scaleY = scale
    rotation = 0f
}