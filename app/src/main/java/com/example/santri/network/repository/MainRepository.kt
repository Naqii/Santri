package com.example.santri.network.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.santri.network.api.ApiService
import com.example.santri.network.model.ApiResponse
import com.example.santri.network.model.SantriItem
import com.example.santri.network.model.SantriResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val service: ApiService) {

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

    fun createSantri(santri: SantriItem): MutableLiveData<ApiResponse<SantriResponse>> {
        val item = MutableLiveData<ApiResponse<SantriResponse>>()
        val api = service.getCreateSantri(
            santri.nis,
            santri.name,
            santri.telp,
            santri.address,
            santri.city,
            santri.province,
            santri.birth,
            santri.email,
            santri.nilaiSikap,
            santri.nilaiMateri,
            santri.nilaiBacaan,
            santri.nilaiHafalan,
            santri.presensiHadir,
            santri.presensiIzin,
            santri.presensiAlfa,
            santri.presensiKeterangan,
            santri.kampusUniv,
            santri.kampusProgdi,
            santri.kampusJurusan,
            santri.kampusGelar
        )
        api.enqueue(object : Callback<SantriResponse> {
            override fun onResponse(
                call: Call<SantriResponse>,
                response: Response<SantriResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        item.postValue(ApiResponse.success(body))
                    } else {
                        item.postValue(ApiResponse.error("No Response", SantriResponse()))
                    }
                }
            }

            override fun onFailure(call: Call<SantriResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }

        })
        return item
    }

    fun updateSantri(santri: SantriItem, id: String): MutableLiveData<ApiResponse<SantriResponse>> {
        val item = MutableLiveData<ApiResponse<SantriResponse>>()
        val api = service.getUpdateSantri(
            id,
            santri.nis,
            santri.name,
            santri.telp,
            santri.address,
            santri.city,
            santri.province,
            santri.birth,
            santri.email,
            santri.nilaiSikap,
            santri.nilaiMateri,
            santri.nilaiBacaan,
            santri.nilaiHafalan,
            santri.presensiHadir,
            santri.presensiIzin,
            santri.presensiAlfa,
            santri.presensiKeterangan,
            santri.kampusUniv,
            santri.kampusProgdi,
            santri.kampusJurusan,
            santri.kampusGelar
        )
        api.enqueue(object : Callback<SantriResponse> {
            override fun onResponse(
                call: Call<SantriResponse>,
                response: Response<SantriResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        item.postValue(ApiResponse.success(body))
                    } else {
                        item.postValue(ApiResponse.error("No Response", SantriResponse()))
                    }
                }
            }

            override fun onFailure(call: Call<SantriResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }

        })
        return item
    }

    fun deleteSantri(id: String): MutableLiveData<ApiResponse<SantriResponse>> {
        val item = MutableLiveData<ApiResponse<SantriResponse>>()
        val api = service.getDeleteSantri(id)
        api.enqueue(object : Callback<SantriResponse> {
            override fun onResponse(
                call: Call<SantriResponse>,
                response: Response<SantriResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        item.postValue(ApiResponse.success(body))
                    } else {
                        item.postValue(ApiResponse.error("No Response", SantriResponse()))
                    }
                }
            }

            override fun onFailure(call: Call<SantriResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }
        })
        return item
    }

}