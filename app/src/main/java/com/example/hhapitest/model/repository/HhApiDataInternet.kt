package com.example.hhapitest.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foundation.model.*
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult
import com.example.hhapitest.views.requestlist.Listener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


typealias DataListener = (Result<String>) -> Unit

class HhApiDataInternet(): Repository {
    private var hhAPI: HhAPI
    private val _stringLiveData: MutableLiveResult<String> = MutableLiveData(PendingResult())
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        hhAPI = retrofit.create(HhAPI::class.java)
    }


    fun getListRequestFromUrl(url: String, dataListener: DataListener?): LiveResult<String> {
        val data: Call<String> = hhAPI.getData(url)
        data.enqueue(object  : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                dataListener?.invoke(ErrorResult<String>(exception = Exception()))
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _stringLiveData.value = SuccessResult(response.body().toString())
                dataListener?.invoke(SuccessResult(response.body().toString()))
            }
        })
        _stringLiveData.value = PendingResult()
        return _stringLiveData
    }

    fun getStringDataBigItem(url: String, dataListener: DataListener? ): LiveResult<String>{
        val stringDataBigItem: MutableLiveResult<String> = MutableLiveData(PendingResult())
        val data: Call<String> = hhAPI.getData(url)
        data.enqueue(object  : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                stringDataBigItem.value = ErrorResult(Exception())
                dataListener?.invoke(ErrorResult<String>(exception = Exception()))
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                stringDataBigItem.value = SuccessResult(response.body().toString())
                dataListener?.invoke(SuccessResult(response.body().toString()))
            }
        })

        return stringDataBigItem
    }

}