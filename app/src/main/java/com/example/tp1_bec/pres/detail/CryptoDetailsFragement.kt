package com.example.tp1_bec.pres.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tp1_bec.R
import com.example.tp1_bec.pres.Singletons
import com.example.tp1_bec.pres.api.CryptoData
import com.example.tp1_bec.pres.list.Crypto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CryptoDetailsFragement : Fragment() {

    private lateinit var textName: TextView
    private lateinit var trigram: TextView
    private lateinit var priceUsd: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crypto_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textName = view.findViewById(R.id.crypto_name)
        trigram = view.findViewById(R.id.crypto_trigram)
        priceUsd = view.findViewById(R.id.crypto_price)
        callApi()
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        refresh.setOnRefreshListener {
            callApi()
            if (refresh.isRefreshing) refresh.isRefreshing = false
        }
    }

    fun callApi(){
        var name = arguments?.getString("nameCrypto")
        name = name!!.toLowerCase().replace(("\\s+".toRegex()), "-")
        Singletons.cryptoApi.getCrypto(name).enqueue(object: Callback<CryptoData>{
            override fun onFailure(call: Call<CryptoData>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<CryptoData>, response: Response<CryptoData>) {
                if (response.isSuccessful && response.body() != null) {
                    val crypto: CryptoData = response.body()!!
                    textName.text = name
                    trigram.text = crypto.data.symbol
                    priceUsd.text = "%.2f".format(crypto.data.priceUsd.toDouble())
                }
            }
        })
    }
}