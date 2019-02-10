package com.aiimpactweekend.review.listener

interface SwipeListener {
    fun isMonoFlingEnabled(): Boolean
    fun isMultiFlingEnabled(): Boolean
    fun isFlingSuccessful(indices: IntArray): Boolean
    fun onFling(indices: IntArray)
    fun onUnsuccessfulFling()
}