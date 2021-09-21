package com.example.hhapitest

import android.app.Application
import com.example.foundation.BaseApplication
import com.example.foundation.model.Repository
import com.example.foundation.views.BaseFragment
import com.example.hhapitest.model.repository.HhApiDataInternet

class AppHhTest: Application(), BaseApplication {
    override val repositories = listOf<Repository>(HhApiDataInternet())
}