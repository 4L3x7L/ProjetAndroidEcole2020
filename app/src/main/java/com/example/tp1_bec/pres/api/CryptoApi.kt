package com.example.tp1_bec.pres.api

import com.example.tp1_bec.pres.list.Crypto
import retrofit2.Call
import retrofit2.http.GET;
import retrofit2.http.Path

interface CryptoApi {
        @GET("assets")
        fun getCryptoList() : Call<CryptoResp>

        @GET("assets/{id}")
        fun getCrypto(@Path("id") id: String ) : Call<CryptoData>
}