package com.example.hhapitest.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hhapitest.model.data.ListRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class HhApiDataInternet(): Repository {
    private var hhAPI: HhAPI
    private val _stringLiveData: MutableLiveData<String> = MutableLiveData()
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        hhAPI = retrofit.create(HhAPI::class.java)
    }
    fun getShortArea(url: String):LiveData<String>{
        val url: String = "https://api.hh.ru/suggests/areas?text=" + url
        val data: Call<String> = hhAPI.getData(url)
        data.enqueue(object  : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _stringLiveData.value = response.body().toString()
            }
        })
        return _stringLiveData
    }
    fun getListRequestFromUrl(url: String): LiveData<String> {
        val data: Call<String> = hhAPI.getData(url)
        data.enqueue(object  : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _stringLiveData.value = response.body().toString()
            }
        })
        return _stringLiveData
    }

}