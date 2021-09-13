package com.example.hhapitest.views

interface UIActions {
    fun toast (message: String)

    fun getString(messageRes: Int, vararg args: Any): String
}