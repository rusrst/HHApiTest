package com.example.hhapitest

import android.app.Application
import com.example.foundation.BaseApplication
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.foundation.model.tasks.factories.ThreadTasksFactory
import com.example.foundation.uiactions.AndroidUIActions
import com.example.hhapitest.model.data.database.RoomRepository
import com.example.hhapitest.model.repository.HhApiDataInternetRepository

class AppHhTest : Application(), BaseApplication {
    private lateinit var dispatcher: MainThreadDispatcher
    private lateinit var simpleTaskFactory: ThreadTasksFactory
    private lateinit var roomRepository: RoomRepository
    private lateinit var uiActions: AndroidUIActions
    override val singletonScopeDependencies by lazy {
        listOf(
            simpleTaskFactory,
            HhApiDataInternetRepository(simpleTaskFactory),
            dispatcher,
            roomRepository,
            uiActions
        )
    }

    override fun onCreate() {
        dispatcher = MainThreadDispatcher()
        simpleTaskFactory = ThreadTasksFactory()
        roomRepository = RoomRepository(this, simpleTaskFactory, dispatcher)
        uiActions = AndroidUIActions(this)
        super.onCreate()
    }
}