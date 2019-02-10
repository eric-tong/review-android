package com.aiimpactweekend.review.util

import android.os.Looper

val isMainThread
    get() = Looper.getMainLooper().isCurrentThread