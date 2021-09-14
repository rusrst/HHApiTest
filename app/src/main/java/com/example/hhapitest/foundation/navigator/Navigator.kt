package com.example.hhapitest.foundation.navigator

import com.example.hhapitest.foundation.views.BaseScreen

interface Navigator {
    fun launch (screen: BaseScreen)


    fun goBack(result: Any? = null)
}
