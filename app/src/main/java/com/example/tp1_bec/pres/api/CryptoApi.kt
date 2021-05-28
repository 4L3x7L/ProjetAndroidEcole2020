package com.example.tp1_bec.pres.api

import retrofit2.Call
import retrofit2.http.GET;

interface CryptoApi {
        @GET("assets")
        fun getCryptoList() : Call<CryptoResp>
}