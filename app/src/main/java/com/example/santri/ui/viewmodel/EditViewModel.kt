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
class EditViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    val dataSantri = MutableLiveData<ArrayList<SantriItem>>()

    fun editSantri(
        santri: SantriItem,
        id: String,
//        idField: String
    ): MutableLiveData<ApiResponse<SantriResponse>> =
        repository.updateSantri(santri, id)

    fun idSantri(
        id: String
    ): MutableLiveData<ApiResponse<SantriResponse>> =
        repository.idSantri(id)

    fun getDataFormDetail(): LiveData<ArrayList<SantriItem>> {
        return  dataSantri
    }
}