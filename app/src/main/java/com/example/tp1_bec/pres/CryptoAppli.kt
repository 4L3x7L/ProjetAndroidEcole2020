package com.example.tp1_bec.pres

import android.app.Application
import android.content.Context

class CryptoAppli : Application(){
    companion object{
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}