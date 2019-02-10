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

fun <T : Comparable<T>> List<T>.median(): T {
    val list = this.toMutableList()
    list.sort()
    return list[list.size / 2]
}

val <T : Any?> List<List<T>>.totalSize: Int
    get() {
        return map { it.size }.sum()
    }

val <T : Any?> List<List<T>>.hasEmptyLists: Boolean
    get() {
        return map { it.size }.contains(0)
    }

val List<List<Int>>.maxCurrentValue: Int
    get() {
        val initialValues = map { it[0] }
        return initialValues.max()!!
    }

val List<List<Int>>.smallestCurrentValue: Int
    get() {
        val initialValues = map { it[0] }
        return initialValues.min()!!
    }

val List<List<Int>>.indexWithLargestCurrentValue: Int
    get() {
        val initialValues = map { it[0] }
        return initialValues.indexOf(initialValues.max())
    }

val List<List<Int>>.indexWithSmallestCurrentValue: Int
    get() {
        val initialValues = map { it[0] }
        return initialValues.indexOf(initialValues.min())
    }

val List<List<Int>>.currentValuesAreAllEqual: Boolean
    get() {
        return map { it[0] }.areAllEqual()
    }