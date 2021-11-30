package com.example.hhapitest

import android.app.Application
import com.example.foundation.BaseApplication
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.foundation.model.tasks.factories.ThreadTasksFactory
import com.example.foundation.uiactions.AndroidUIActions
import com.example.hhapitest.model.data.database.RoomRepository
import com.example.hhapitest.model.repository.HhApiDataInternetRepository

class AppHhTest: Application(), BaseApplication {
    private val dispatcher by lazy { MainThreadDispatcher() }
    private val simpleTaskFactory by lazy {ThreadTasksFactory()}
    private val roomRepository by lazy {RoomRepository(this, simpleTaskFactory, dispatcher)}
    private val uiActions by lazy { AndroidUIActions(this) }
    override val singletonScopeDependencies by lazy {listOf(simpleTaskFactory, HhApiDataInternetRepository(simpleTaskFactory), dispatcher, roomRepository, uiActions)}
}