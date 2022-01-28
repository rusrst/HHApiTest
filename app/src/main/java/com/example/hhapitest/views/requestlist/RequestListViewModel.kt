package com.example.hhapitest.views.requestlist


import androidx.lifecycle.SavedStateHandle
import com.example.foundation.model.*
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.hhapitest.model.internet.HhApiDataInternetRepository
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.database.RoomRepository
import com.example.hhapitest.model.json.dataclassesforjson.ListRequest
import com.example.hhapitest.model.json.dataclassesforjson.ShortItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

typealias Listener = (String)->Unit

class RequestListViewModel(
    screen: RequestList.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val repository: HhApiDataInternetRepository,
    private val taskFactory: TaskFactory,
    savedStateHandle: SavedStateHandle,
    dispatcher: Dispatcher,
    private val roomRepository: RoomRepository
) : BaseViewModel(dispatcher, taskFactory) {

    var urlItem: String? = null
    fun tryAgain(){
        load()
    }
        fun getListRequestFromUrl() = load()


        private val _data = MutableLiveResult<List<ShortItem>?>(PendingResult())
        val liveListShortItem: LiveResult<List<ShortItem>?> = _data
        fun launch (screen: BaseScreen, result: Any?){
            navigator.launch(screen, result)
        }

    private fun load(){
        if (urlItem == null) navigator.goBack()
        taskFactory.async {
            val data= repository.getRequestFromUrl(urlItem ?: "", null)
            val list = Json.decodeFromString<ListRequest>(data)
            return@async list.items
        }.into(_data)
    }
}

