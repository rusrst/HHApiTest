package com.example.hhapitest.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.foundation.model.*
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.foundation.views.MutableLiveResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


typealias DataListener = (Result<String>) -> Unit

class HhApiDataInternetRepository(private val taskFactory: TaskFactory): HHApiDataRepository {
    private var hhAPI: HhAPI
    private val _stringLiveData: MutableLiveResult<String> = MutableLiveData(PendingResult())
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        hhAPI = retrofit.create(HhAPI::class.java)
    }


    override fun getRequestFromUrl(url: String, dataListener: DataListener?): Task<String> =
        taskFactory.async {
            val data: Call<String> = hhAPI.getData(url)
                val response = data.execute()
                return@async response.body().toString()

        }


/*
        val data: Call<String> = hhAPI.getData(url)
        data.enqueue(object  : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _stringLiveData.value = ErrorResult(Exception())
                dataListener?.invoke(ErrorResult<String>(exception = Exception()))
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _stringLiveData.value = SuccessResult(response.body().toString())
                dataListener?.invoke(SuccessResult(response.body().toString()))
            }
        })
        _stringLiveData.value = PendingResult()
        return _stringLiveData

 */

}