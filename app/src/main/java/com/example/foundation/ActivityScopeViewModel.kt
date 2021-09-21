package com.example.foundation

import androidx.lifecycle.ViewModel
import com.example.foundation.navigator.IntermediateNavigator
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions


const val ARG_SCREEN = "ARG_SCREEN"


class ActivityScopeViewModel(val uiActions: UIActions,
                    val navigator: IntermediateNavigator): ViewModel(),
                    UIActions by uiActions,
                    Navigator by navigator {


    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }
}