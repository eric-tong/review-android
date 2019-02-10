package com.aiimpactweekend.review.util

import kotlin.math.abs

fun minAbs(a: Float, b: Float): Float {
    return if (abs(a) > abs(b))
        if (a > 0) b else -b
    else a
}