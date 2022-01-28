package com.example.hhapitest.views.listofroomrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.BaseViewModel
import com.example.hhapitest.model.database.RoomRepository
import com.example.hhapitest.model.database.dataclassroom.RequestRoom
import com.example.hhapitest.model.internet.HhApiDataInternetRepository

class ListOfRoomRequestViewModel(
    screen: ListOfRoomRequest.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val repository: HhApiDataInternetRepository,
    private val taskFactory: TaskFactory,
    savedStateHandle: SavedStateHandle,
    dispatcher: Dispatcher,
    private val roomRepository: RoomRepository,
) : BaseViewModel(dispatcher, taskFactory) {
    fun getListOfRequests(): LiveData<List<RequestRoom>?>{
        return roomRepository.getListRequests()
    }
    fun launch (screen: BaseScreen, result: Any?){
        navigator.launch(screen, result)
    }
}