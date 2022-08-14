package com.example.santri.network.api

import com.example.santri.network.model.SantriResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("santri")
    fun getSantri(): Call<SantriResponse>
}