package com.example.foundation.navigator

import com.example.foundation.utils.ResourceActions
import com.example.foundation.views.BaseScreen

class IntermediateNavigator : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()
    override fun launch(screen: BaseScreen, result: Any?) = targetNavigator {
        it.launch(screen, result)
    }

    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result)
    }

    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    fun clear() {
        targetNavigator.clear()
    }

}