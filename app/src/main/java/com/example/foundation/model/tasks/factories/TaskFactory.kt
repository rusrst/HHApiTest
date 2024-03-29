package com.example.foundation.model.tasks.factories

import com.example.foundation.model.tasks.Task


typealias TaskBody<T> = () -> T

interface TaskFactory {

    fun <T> async(body: TaskBody<T>): Task<T>
}