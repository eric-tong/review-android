package com.aiimpactweekend.review.util

import android.app.Activity
import android.view.View

fun Activity.setFullscreen() {
    window.decorView.apply {
        systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}