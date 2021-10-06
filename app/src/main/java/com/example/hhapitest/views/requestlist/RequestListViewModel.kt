package com.example.hhapitest.views.requestlist


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.example.foundation.model.ErrorResult
import com.example.foundation.model.PendingResult
import com.example.foundation.model.Result
import com.example.foundation.model.SuccessResult
import com.example.hhapitest.model.repository.HhApiDataInternet
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.data.ListRequest
import com.example.hhapitest.model.data.ShortItem
import com.example.hhapitest.model.repository.DataListener
import com.google.gson.Gson

typealias Listener = (String)->Unit
typealias LiveListShortItem = LiveData<List<ShortItem>?>

class RequestListViewModel(
    screen: RequestList.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val repository: HhApiDataInternet,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    //listener is needed to update the value of a variable
    //we don't subscribe to the original return value in the fragment
    private val dataListener: DataListener ={result ->
        when(result){
            is ErrorResult -> _data.postValue(result)
            is PendingResult -> _data.postValue(PendingResult())
            is SuccessResult -> _data.postValue(result)
        }
    }

    fun getListRequestFromUrl() = repository.getListRequestFromUrl("https://api.hh.ru/vacancies?employer_id=1025275&area=1530&period=1&per_page=100", dataListener)

    private val _data:MutableLiveResult<String> = MutableLiveData(PendingResult())
    private var _liveListShortItem: LiveResult<List<ShortItem>?> = Transformations.map(_data) { result ->
        if (result is SuccessResult){
        val gson = Gson()
        val data = gson.fromJson(result.data, ListRequest::class.java)
        return@map SuccessResult(data.items)
    }
        return@map result as Result<List<ShortItem>?>
    }
    val liveListShortItem: LiveResult<List<ShortItem>?> = _liveListShortItem
    fun launch (screen: BaseScreen, result: Any?){
        navigator.launch(screen, result)
    }
}