package com.example.hhapitest

import android.app.Application
import com.example.foundation.BaseApplication
import com.example.foundation.model.Repository
import com.example.foundation.model.tasks.SimpleTasksFactory
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.hhapitest.model.repository.HhApiDataInternetRepository

class AppHhTest: Application(), BaseApplication {
    private val dispatcher = MainThreadDispatcher()
    private val simpleTaskFactory = SimpleTasksFactory()
    override val singletonScopeDependencies = listOf(simpleTaskFactory, HhApiDataInternetRepository(simpleTaskFactory), dispatcher)
}