package com.example.tp1_bec.pres

import com.example.tp1_bec.pres.CryptoAppli.Companion.context
import com.example.tp1_bec.pres.api.CryptoApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class Singletons {
    companion object {
        var cache: Cache = Cache(File(context?.getCacheDir(), "responses"), 10 * 1024 * 1024 )// 10 MiB
        val okhttpClient: OkHttpClient = OkHttpClient().newBuilder()
                .cache(cache)
                .build()


        val cryptoApi: CryptoApi = Retrofit.Builder()
            .baseUrl("https://api.coincap.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
            .create(CryptoApi::class.java)
    }
}

