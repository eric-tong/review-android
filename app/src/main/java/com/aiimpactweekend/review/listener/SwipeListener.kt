package com.aiimpactweekend.review.listener

import com.aiimpactweekend.review.constants.Direction

interface SwipeListener {
    fun isMonoFlingEnabled(): Boolean
    fun isMultiFlingEnabled(): Boolean
    fun isFlingSuccessful(indices: IntArray): Boolean
    fun onFling(direction: Direction)
    fun onUnsuccessfulFling()
}