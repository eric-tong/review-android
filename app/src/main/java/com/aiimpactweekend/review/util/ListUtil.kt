package com.aiimpactweekend.review.util

import android.os.Build.VERSION_CODES.N

fun <T : Any?> List<T>?.isNullorEmpty(): Boolean {
    return this == null || isEmpty()
}

fun <T : Any?> MutableList<T>.shift(): T {
    val firstElement = this.first()
    this.remove(firstElement)
    return firstElement
}

fun <T : Any?> MutableList<T>.pop(): T {
    val lastElement = this.last()
    this.remove(lastElement)
    return lastElement
}

fun <T : Any?> List<T>.areAllEqual(): Boolean {
    val firstElement = first()
    forEach { if (it != firstElement) return false }
    return true
}

fun Array<String>.getRandom(): Array<String> {
    var n = 3
    val result = arrayOf("", "", "")
    while (n > 0) {
        val rand = this.random()
        if (!result.contains(rand)) {
            result[n-1] = rand
            n--
        }
    }
    return result
}