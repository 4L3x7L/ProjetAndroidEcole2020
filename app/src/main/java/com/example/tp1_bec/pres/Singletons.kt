package com.example.tp1_bec.pres

import com.example.tp1_bec.pres.api.CryptoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Singletons {
    companion object {
        val cryptoApi: CryptoApi = Retrofit.Builder()
            .baseUrl("https://api.coincap.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CryptoApi::class.java)
    }
}

