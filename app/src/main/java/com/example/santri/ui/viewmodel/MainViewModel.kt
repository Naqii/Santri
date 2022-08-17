package com.example.santri.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.santri.network.model.ApiResponse
import com.example.santri.network.model.SantriResponse
import com.example.santri.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    //GET All
    fun getSantri(): LiveData<ApiResponse<SantriResponse>> = repository.getSantri()

    //GET BY ID
    fun setSrcSantri(id: String): LiveData<ApiResponse<SantriResponse>> =
        repository.idSantri(id)
}