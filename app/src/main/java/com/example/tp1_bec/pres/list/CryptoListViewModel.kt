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
    val cryptoList : MutableLiveData<List<Crypto>> = MutableLiveData()

    init {
        callApi()
    }


    private fun callApi(){
        Singletons.cryptoApi.getCryptoList().enqueue(object: Callback<CryptoResp> {
            override fun onFailure(call: Call<CryptoResp>, t: Throwable) {
                //showList(getListFromCache())
                //Toast.makeText(activity, "Vous etes off-line", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<CryptoResp>, response: Response<CryptoResp>) {
                if (response.isSuccessful && response.body() != null) {
                    val cryptoResp: CryptoResp = response.body()!!
                    cryptoList.value = cryptoResp.data
                    //saveListIntoCache(cryptoResp.data)
                    //showList(cryptoResp.data)
                }
            }
        })
    }
}
