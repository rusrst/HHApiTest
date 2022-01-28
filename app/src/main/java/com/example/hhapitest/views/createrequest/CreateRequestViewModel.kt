package com.example.hhapitest.views.createrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.foundation.model.ErrorResult
import com.example.foundation.model.PendingResult
import com.example.foundation.model.Result
import com.example.foundation.model.SuccessResult
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.R
import com.example.hhapitest.model.database.RoomRepository
import com.example.hhapitest.model.database.dataclassroom.AreaRoom
import com.example.hhapitest.model.json.dataclassesforjson.Area
import com.example.hhapitest.model.json.Json
import com.example.hhapitest.model.internet.HhApiDataInternetRepository

class CreateRequestViewModel(screen: CreateRequest.Screen,
                             private val navigator: Navigator,
                             private val uiActions: UIActions,
                             private val repository: HhApiDataInternetRepository,
                             private val dispatcher: Dispatcher,
                             private val roomRepository: RoomRepository,
                             private val taskFactory: TaskFactory
) : BaseViewModel(dispatcher, taskFactory) {
    private var currentTask: Task<List<Area>>? = null
    private val _state = MutableLiveData(State(null))
    val state: LiveData<State> = _state

    private val _listAreas = MutableLiveResult<List<Area>>(PendingResult())
    val listAreas: LiveResult<List<Area>> = _listAreas

    private val _checkDatabaseOfAreas = MutableLiveResult<AreaRoom?>(PendingResult())
    val checkingDatabaseOfAreas: LiveResult<Boolean> = Transformations.map(_checkDatabaseOfAreas){result ->
        if (result is SuccessResult && result.data != null) return@map SuccessResult(true)
        else if (result is SuccessResult) return@map SuccessResult(false)
        else return@map ErrorResult(Exception())
    }

    fun tryAgain(func: (() -> Unit)? = null){
        func?.invoke()
    }
    fun getListAreas() = load("https://api.hh.ru/areas")


    private fun load(url: String){
        _listAreas.value = PendingResult()
        taskFactory.async {
            val data = repository.getRequestFromUrlWithJsonParser(url) {
                Json.getListAreas(it)
            }
            addAreaRoomList(data)
        }.safeEnqueue{
            _listAreas.value = SuccessResult(mutableListOf())
        }
    }
    private fun addAreaRoomList (listArea: List<Area>){
        val list = mutableListOf<AreaRoom>()
        listArea.forEach {
            val areaRoom = AreaRoom(it.id?.toInt() ?: throw Exception("ID IS STRING!!!"), parentId = it.parentId, name = it.name)
            list.add(areaRoom)
        }
        roomRepository.addAreasRoom(list)
    }
    fun checkDatabaseOfAreas() {
        roomRepository.checkDatabaseOfAreas().enqueue(dispatcher){
            _checkDatabaseOfAreas.value = it
        }
    }
    fun checkDatabaseOfAreasEnd(result: Result<Boolean>){
        if (result is SuccessResult && result.data) _state.value = State(null)
        else if((result is SuccessResult && !result.data)){
            _state.value = State(DataSimpleDialog(uiActions.getString(R.string.no_areas_title), uiActions.getString(R.string.no_areas_text)))
        }
    }


    data class State(
        val dataSimpleDialog: DataSimpleDialog?
    ) {
        val showDialog: Boolean get() =  dataSimpleDialog != null
    }
    data class DataSimpleDialog(val title: String?,
    val text: String?)

    fun getAreasOnNameFromRoomNoLiveData(str: String) = roomRepository.getAreasOnNameFromRoomNoLiveData(str)
    fun getEmployersRequestNoLiveData(str: String) = repository.getRequestFromUrlEmployersRequest(str)
}