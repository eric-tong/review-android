package com.aiimpactweekend.review.util

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

fun MotionEvent.isWithin(bounds: Rect): Boolean {
    return bounds.contains(x.toInt(), y.toInt())
}

fun MotionEvent.isAboveMiddle(view: View): Boolean {
    return rawY - view.y < view.height / 2
}