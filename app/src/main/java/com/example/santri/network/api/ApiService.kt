package com.example.santri.network.api

import com.example.santri.network.model.SantriItem
import com.example.santri.network.model.SantriResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //GET ALL
    @GET("data")
    fun getSantri(): Call<SantriResponse>

    //POST/ADD Data
    @POST("data")
    fun getCreateSantri(
        @Body santri: SantriItem,

    ): Call<SantriResponse>
}