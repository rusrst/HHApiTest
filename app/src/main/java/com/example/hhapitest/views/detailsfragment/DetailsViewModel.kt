package com.example.hhapitest.views.detailsfragment


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.foundation.model.ErrorResult
import com.example.foundation.model.PendingResult
import com.example.foundation.model.Result
import com.example.foundation.model.SuccessResult
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.data.ListRequest
import com.example.hhapitest.model.data.ShortItem
import com.example.hhapitest.model.repository.DataListener
import com.example.hhapitest.model.repository.HhApiDataInternet
import com.example.hhapitest.views.requestlist.RequestList
import com.google.gson.Gson
import java.lang.Exception


class DetailsViewModel(screen: DetailsFragment.Screen,
                       private val navigator: Navigator,
                       private val uiActions: UIActions,
                       private val repository: HhApiDataInternet,) : BaseViewModel() {
    var urlItem: String? = null
    private val _data:MutableLiveResult<String> = MutableLiveData(PendingResult())
    //listener is needed to update the value of a variable
    //we don't subscribe to the original return value in the fragment
    private val dataListener: DataListener ={result ->
        when(result){
            is ErrorResult -> _data.postValue(result)
            is PendingResult -> _data.postValue(PendingResult())
            is SuccessResult -> _data.postValue(result)
        }
    }

    fun getStringDataBigItem (url: String) =
        if (url != "") repository.getStringDataBigItem(url, dataListener)
        else throw Exception("IllegalArgumentException")

    private var _liveBigItem: LiveResult<String?> = Transformations.map(_data) { result ->
        if (result is SuccessResult){
            // TODO: 01.10.2021
        }
        return@map result as Result<String?>
    }
    val liveBigItem: LiveResult<String?> = _liveBigItem


}