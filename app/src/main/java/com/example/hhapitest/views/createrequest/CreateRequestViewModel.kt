package com.example.hhapitest.views.createrequest

import android.util.Log
import com.example.foundation.model.PendingResult
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.data.*
import com.example.hhapitest.model.data.database.RoomRepository
import com.example.hhapitest.model.json.Json
import com.example.hhapitest.model.repository.HhApiDataInternetRepository
import java.lang.Exception

class CreateRequestViewModel(screen: CreateRequest.Screen,
                             private val navigator: Navigator,
                             private val uiActions: UIActions,
                             private val repository: HhApiDataInternetRepository,
                             private val dispatcher: Dispatcher,
                             private val roomRepository: RoomRepository
) : BaseViewModel(dispatcher) {
    private val tasks = mutableSetOf<Task<*>>()
    private var currentTask: Task<List<Area>>? = null


    private val _data = MutableLiveResult<List<Area>>(PendingResult())
    val data: LiveResult<List<Area>> = _data


    fun tryAgain(func: (() -> Unit)? = null){
        func?.invoke()
    }
    fun getListAreas() = load("https://api.hh.ru/areas")
    private fun load(url: String){
        currentTask = repository.getRequestFromUrlWithJsonParser(url, null){
            Json.getListAreas(it)
        }.also { task->
            _data.value = PendingResult()
            task.enqueue(dispatcher){
                currentTask = null
                _data.value = it
            }
        }
    }
    fun addAreaRoomList (listArea: List<Area>){
        val list = mutableListOf<AreaRoom>()
        listArea.forEach {
            val areaRoom = AreaRoom(it.id?.toInt() ?: throw Exception("ID IS STRING!!!"), parentId = it.parentId, name = it.name)
            list.add(areaRoom)
        }
        roomRepository.addAreasRoom(list)
        Log.d("TAG", "DB END")
    }
}