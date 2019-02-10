package com.aiimpactweekend.review.util

import android.arch.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue }

fun <T : Any?> MutableLiveData<T>.threadSafeSet(value: T) {
    if (isMainThread) setValue(value)
    else postValue(value)
}