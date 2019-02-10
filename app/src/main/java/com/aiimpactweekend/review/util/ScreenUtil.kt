package com.aiimpactweekend.review.util

import android.app.Activity
import android.content.res.Resources
import android.util.DisplayMetrics


val dp_in_px = Resources.getSystem().displayMetrics.density
val Int.dp: Int
    get() = (this * dp_in_px).toInt()

val Activity.screenSize: Array<Int>
    get() {
        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val screenWidth = metrics.widthPixels
        val screenHeight = metrics.heightPixels
        return arrayOf(screenWidth, screenHeight)
    }