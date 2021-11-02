package com.example.foundation.model.tasks


typealias TaskBody<T> = () -> T
interface TaskFactory {

    fun<T> async (body: TaskBody<T>): Task<T>
}