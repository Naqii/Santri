package com.example.santri.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.santri.network.model.ApiResponse
import com.example.santri.network.model.SantriItem
import com.example.santri.network.model.SantriResponse
import com.example.santri.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun createSantri(santri: SantriItem): MutableLiveData<ApiResponse<SantriResponse>> =
        repository.createSantri(santri)
}