package com.example.foundation.model.tasks.factories

import android.os.Handler
import android.os.HandlerThread
import com.example.foundation.model.tasks.AbstractTask
import com.example.foundation.model.tasks.SynchronizedTask
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.TaskListener

class HandlerThreadTaskFactory() : TaskFactory {

    private val thread = HandlerThread(javaClass.simpleName)

    init {
        thread.start()
    }

    private val handler = Handler(thread.looper)
    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(HandlerTreadTask(body))
    }

    private inner class HandlerTreadTask<T>(private val body: TaskBody<T>) : AbstractTask<T>() {

        private var thread: Thread? = null
        override fun doEnqueue(listener: TaskListener<T>) {
            val runnable = Runnable {
                thread = Thread {
                    executeBody(body, listener)
                }
                thread?.start()
                thread?.join()
            }
            handler.post(runnable)
        }

        override fun doCancel() {
            thread?.interrupt()
        }

    }
}