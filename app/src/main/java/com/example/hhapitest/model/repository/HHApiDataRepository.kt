package com.example.hhapitest.model.repository

import androidx.lifecycle.LiveData
import com.example.foundation.model.FinalResult
import com.example.foundation.model.Repository
import com.example.foundation.model.tasks.Task
import com.example.foundation.views.LiveResult

interface HHApiDataRepository : Repository {
    fun getRequestFromUrl(url: String, dataListener: DataListener?): Task<FinalResult<String>>
}