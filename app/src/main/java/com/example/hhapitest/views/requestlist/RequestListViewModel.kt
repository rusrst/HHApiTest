package com.example.hhapitest.views.requestlist


import androidx.lifecycle.SavedStateHandle
import com.example.hhapitest.model.repository.HhApiDataInternet
import com.example.hhapitest.views.Navigator
import com.example.hhapitest.views.UIActions
import com.example.hhapitest.views.base.BaseViewModel

class RequestListViewModel(
    screen: RequestList.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val repository: HhApiDataInternet,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _data = repository.getListRequestFromUrl("https://api.hh.ru/vacancies?employer_id=1025275&area=1530&period=1&per_page=100")
    val data = _data
}