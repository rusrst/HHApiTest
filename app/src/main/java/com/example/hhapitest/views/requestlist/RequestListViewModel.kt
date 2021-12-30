package com.example.hhapitest.views.requestlist


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.example.foundation.model.*
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.hhapitest.model.repository.HhApiDataInternetRepository
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.data.database.RoomRepository
import com.example.hhapitest.model.data.dataclassesforjson.ListRequest
import com.example.hhapitest.model.data.dataclassesforjson.ShortItem
import com.google.gson.Gson

typealias Listener = (String)->Unit
typealias LiveListShortItem = LiveData<List<ShortItem>?>

class RequestListViewModel(
    screen: RequestList.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val repository: HhApiDataInternetRepository,
    private val taskFactory: TaskFactory,
    savedStateHandle: SavedStateHandle,
    dispatcher: Dispatcher,
    private val roomRepository: RoomRepository
) : BaseViewModel(dispatcher) {
    //listener is needed to update the value of a variable
    //we don't subscribe to the original return value in the fragment
    /*private val dataListener: DataListener = {result ->
        when(result){
            is ErrorResult -> _data.postValue(result)
            is PendingResult -> _data.postValue(PendingResult())
            is SuccessResult -> _data.postValue(result)
        }
    }

     */

    var urlItem: String? = null
    fun tryAgain(){
        load()
    }
        fun getListRequestFromUrl() = load()


        private val _data = MutableLiveResult<String>(PendingResult())
        private var _liveListShortItem: LiveResult<List<ShortItem>?> = Transformations.map(_data) { result ->
            if (result is SuccessResult){
                val gson = Gson()
                try{
                val data = gson.fromJson(result.data, ListRequest::class.java)
                return@map SuccessResult(data.items)}
                catch (e: Exception){
                    return@map ErrorResult(e)
                }
            }
            return@map result as Result<List<ShortItem>?>
        }
        val liveListShortItem: LiveResult<List<ShortItem>?> = _liveListShortItem
        fun launch (screen: BaseScreen, result: Any?){
            navigator.launch(screen, result)
        }

        private fun load(){
            if (urlItem == null) navigator.goBack()
            repository.getRequestFromUrl(urlItem ?: "", null)
                .into(_data)

        }
}

