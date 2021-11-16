package com.example.hhapitest

import android.app.Application
import com.example.foundation.BaseApplication
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.foundation.model.tasks.factories.ThreadTasksFactory
import com.example.hhapitest.model.repository.HhApiDataInternetRepository

class AppHhTest: Application(), BaseApplication {
    private val dispatcher = MainThreadDispatcher()
    private val simpleTaskFactory = ThreadTasksFactory()
    override val singletonScopeDependencies = listOf(simpleTaskFactory, HhApiDataInternetRepository(simpleTaskFactory), dispatcher)
}