package com.example.hhapitest

import android.app.Application
import com.example.hhapitest.model.repository.HhApiDataInternet

class AppHhTest: Application() {
    val models = listOf<Any>(HhApiDataInternet())
}