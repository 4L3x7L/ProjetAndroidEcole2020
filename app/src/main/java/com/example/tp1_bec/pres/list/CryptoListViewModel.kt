package com.example.tp1_bec.pres.list

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp1_bec.pres.Singletons
import com.example.tp1_bec.pres.api.CryptoResp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoListViewModel : ViewModel() {
    val cryptoList : MutableLiveData<CryptoModel> = MutableLiveData()

    init {
        callApi()
    }


    fun callApi(){
        Singletons.cryptoApi.getCryptoList().enqueue(object: Callback<CryptoResp> {
            override fun onFailure(call: Call<CryptoResp>, t: Throwable) {
                cryptoList.value = CryptoError
            }

            override fun onResponse(call: Call<CryptoResp>, response: Response<CryptoResp>) {
                if (response.isSuccessful && response.body() != null) {
                    val cryptoResp: CryptoResp = response.body()!!
                    cryptoList.value = CryptoSuccess(cryptoResp.data)
                }
            }
        })
    }
}
