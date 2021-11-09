package com.example.foundation.model.tasks

import android.icu.number.NumberFormatter
import com.example.foundation.model.FinalResult
import com.example.foundation.model.tasks.dispatchers.Dispatcher


typealias TaskListener<T> = (FinalResult<T>) -> Unit

class CancelledException : Exception()
interface Task<T> {
    fun await (): T

    fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>)

    fun cancel()
}