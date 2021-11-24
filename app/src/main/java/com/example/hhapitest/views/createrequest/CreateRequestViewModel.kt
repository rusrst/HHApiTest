package com.example.hhapitest.views.createrequest

import androidx.lifecycle.Transformations
import com.example.foundation.model.PendingResult
import com.example.foundation.model.Result
import com.example.foundation.model.SuccessResult
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.data.*
import com.example.hhapitest.model.json.Json
import com.example.hhapitest.model.repository.HhApiDataInternetRepository
import com.google.gson.Gson

class CreateRequestViewModel(screen: CreateRequest.Screen,
                             private val navigator: Navigator,
                             private val uiActions: UIActions,
                             private val repository: HhApiDataInternetRepository,
                             private val dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {
    private val tasks = mutableSetOf<Task<*>>()
    private var currentTask: Task<List<Area>>? = null


    private val _data = MutableLiveResult<List<Area>>(PendingResult())
    val data: LiveResult<List<Area>> = _data


    //val liveListAreas: LiveResult<List<Area>?> = _liveListAreas

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

/*
     private fun load(url: String){
        currentTask = repository.getRequestFromUrl(url, null)
            //.into(_data)
            .also { task ->
                currentTask = task
                _data.value = PendingResult()
                task.enqueue(dispatcher){
                    if (it is SuccessResult) currentTask = null
                    _data.value = it
                }
            }
    }


    private var _liveListAreas: LiveResult<List<Area>?> = Transformations.map(_data) { result ->
        if (result is SuccessResult){
            val gson = Gson()
            val data = gson.fromJson(result.data, Array<Area>::class.java).toList()
            return@map SuccessResult(data)
        }
        return@map result as Result<List<Area>?>
    }

 */
}