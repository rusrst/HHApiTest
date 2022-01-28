package com.example.hhapitest.model.internet

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface HhAPI {
    @Headers("User-Agent: User-Agent")
    @GET
    fun getData(
        @Url url: String,
    ): Call<String>
}


