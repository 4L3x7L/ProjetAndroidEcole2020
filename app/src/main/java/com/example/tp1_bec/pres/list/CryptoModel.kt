package com.example.tp1_bec.pres.list

sealed class CryptoModel

data class CryptoSuccess(val cryptoList: List<Crypto>) : CryptoModel()
object CryptoError : CryptoModel()