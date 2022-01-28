package com.example.hhapitest.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.foundation.model.*
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.factories.TaskFactory
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.model.data.dataclassesforjson.Area
import com.example.hhapitest.model.json.GetListAreas
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


typealias DataListener = (Result<String>) -> Unit
typealias DataListenerList = (Result<List<Area>>) -> Unit

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

     fun getRequestFromUrl(url: String, dataListener: DataListener?): String
        {
            val data: Call<String> = hhAPI.getData(url)
            val response = data.execute()
            return response.body().toString()
        }

    fun getRequestFromUrlWithJsonParser(
        url: String,
        jsonParser: GetListAreas
    ):List<Area>
    {
        val call: Call<String> = hhAPI.getData(url)
        val response = call.execute()
        val data = response.body().toString()
        return jsonParser(data)
    }

    fun getRequestFromUrlEmployersRequest(
        request: String
    ): Task<String>{
        return taskFactory.async {
            val data: Call<String> = hhAPI.getData("https://api.hh.ru/employers?text=$request")
            try {
                val response = data.execute()
                return@async response.body().toString()
            }
            catch (E: Exception){
                return@async ""
            }
        }
    }
}
