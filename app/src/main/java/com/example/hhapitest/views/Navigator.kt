package com.example.hhapitest.views

import com.example.hhapitest.views.base.BaseScreen

interface Navigator {
    fun launch (screen: BaseScreen)


    fun goBack(result: Any? = null)
}
