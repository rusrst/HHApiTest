package com.example.foundation.navigator

import com.example.foundation.views.BaseScreen

interface Navigator {
    fun launch (screen: BaseScreen, result: Any? = null)


    fun goBack(result: Any? = null)
}
