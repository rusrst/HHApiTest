package com.example.foundation.model.tasks

import android.icu.number.NumberFormatter
import com.example.foundation.model.FinalResult


typealias TaskListener<T> = (FinalResult<T>) -> Unit
interface Task<T> {
    fun await (): T

    fun enqueue(listener: TaskListener<T>)

    fun cancel()
}