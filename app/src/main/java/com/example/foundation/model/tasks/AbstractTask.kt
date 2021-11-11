package com.example.foundation.model.tasks

import com.example.foundation.model.ErrorResult
import com.example.foundation.model.FinalResult
import com.example.foundation.model.SuccessResult
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.utils.delegates.Await
import java.lang.Exception
import java.net.CacheRequest

abstract class AbstractTask<T>() : Task<T> {
    private var finalResult by Await<FinalResult<T>>()

    final override fun await(): T {
        val wrapperListener: TaskListener<T> = {
            finalResult = it
        }
        doEnqueue(wrapperListener)
        try {
            when (val result = finalResult){
                is ErrorResult -> throw result.exception
                is SuccessResult -> return result.data
            }

        } catch(e: Exception) {
            if (e is InterruptedException){
                cancel()
                throw CancelledException(e)
            }
            else throw e
        }
    }

    final override fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>) {
        val wrapperListener: TaskListener<T> = {
            finalResult = it
            dispatcher.dispatch {
                listener(finalResult)
            }
        }
        doEnqueue(wrapperListener)
    }

    final override fun cancel() {
        finalResult = ErrorResult(CancelledException())
        doCancel()
    }

    abstract fun doEnqueue(listener: TaskListener<T>)
    abstract fun doCancel()
}