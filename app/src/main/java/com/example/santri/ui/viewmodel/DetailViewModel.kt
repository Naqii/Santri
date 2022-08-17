package com.example.santri.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.santri.network.model.ApiResponse
import com.example.santri.network.model.SantriItem
import com.example.santri.network.model.SantriResponse
import com.example.santri.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    val listUser = MutableLiveData<ArrayList<SantriItem>>()

    fun setDetailUser(id: String): LiveData<ApiResponse<SantriResponse>> =
        repository.idSantri(id)

    fun getDetailUser(): LiveData<ArrayList<SantriItem>> {
        return listUser
    }

    fun deleteSantri(id: String): MutableLiveData<ApiResponse<SantriResponse>> =
        repository.deleteSantri(id)
}