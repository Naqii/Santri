package com.example.santri.network.repository

import androidx.lifecycle.MutableLiveData
import com.example.santri.network.api.ApiService
import com.example.santri.network.model.ApiResponse
import com.example.santri.network.model.SantriResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val service: ApiService){

    //GET ALL
    fun getSantri(): MutableLiveData<ApiResponse<SantriResponse>> {
        val item = MutableLiveData<ApiResponse<SantriResponse>>()
        val api = service.getSantri()
        api.enqueue(object : Callback<SantriResponse> {
            override fun onResponse(
                call: Call<SantriResponse>,
                response: Response<SantriResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        item.value = ApiResponse.success(body)
                    } else {
                        item.value = ApiResponse.error("No response", SantriResponse())
                    }
                }
            }

            override fun onFailure(call: Call<SantriResponse>, t: Throwable) {
                item.value = ApiResponse.error(
                    "Error reported [UNKNOWN]",
                    SantriResponse()
                )
            }
        })
        return item
    }
}