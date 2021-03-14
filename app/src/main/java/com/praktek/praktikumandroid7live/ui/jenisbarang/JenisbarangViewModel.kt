package com.praktek.praktikumandroid7live.ui.jenisbarang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praktek.praktikumandroid7live.model.Jenisbarang
import com.praktek.praktikumandroid7live.model.JenisbarangData
import com.praktek.praktikumandroid7live.model.JenisbarangResponse
import com.praktek.praktikumandroid7live.network.Api
import kotlinx.coroutines.launch
import retrofit2.Response

class JenisbarangViewModel : ViewModel() {
    private val _response = MutableLiveData<Jenisbarang>()
    val createResponse = MutableLiveData<Response<JenisbarangResponse>>()

    val response: LiveData<Jenisbarang>
        get() = _response

    init {
        setResponse()
    }

    private fun setResponse() {
        viewModelScope.launch {
            try {
                val listResult = Api.retrofitService.getJenisbarang()
                _response.value = listResult
            } catch (e: Exception) {
                _response.value = null
            }
        }
    }

    fun create(jenisbarangData: JenisbarangData) {
        viewModelScope.launch {
            val response = Api.retrofitService.create(jenisbarangData)
            createResponse.value = response
        }
    }


}