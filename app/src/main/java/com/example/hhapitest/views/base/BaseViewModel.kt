package com.example.hhapitest.views.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hhapitest.utils.Event


typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
open class BaseViewModel: ViewModel() {
    open fun onResult(result: Any){

    }
}
