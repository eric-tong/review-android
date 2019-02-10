package com.aiimpactweekend.review.adapter

import android.view.View

interface CardAdapter {
    fun loadCurrent(view: View, index: Int)
    fun loadNext(view: View, index: Int)
}