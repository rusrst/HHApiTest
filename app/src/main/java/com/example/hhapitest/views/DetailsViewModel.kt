package com.example.hhapitest.views

import androidx.lifecycle.SavedStateHandle
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.foundation.views.BaseViewModel
import com.example.hhapitest.model.repository.HhApiDataInternet
import com.example.hhapitest.views.requestlist.RequestList

class DetailsViewModel(
    screen: RequestList.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val repository: HhApiDataInternet,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

}