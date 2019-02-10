package com.aiimpactweekend.review.util

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