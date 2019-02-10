package com.aiimpactweekend.review.animator

import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator


fun View.bounceBack(): ViewPropertyAnimator {
    return animate().translationX(0f)
            .translationY(0f)
            .rotation(0f)
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(1.5f))
}

fun View.scaleTo(scale: Float): ViewPropertyAnimator {
    return animate().scaleX(scale)
            .scaleY(scale)
            .setDuration(300)
            .setInterpolator(FastOutSlowInInterpolator())
}