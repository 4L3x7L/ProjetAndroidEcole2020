package com.example.tp1_bec.pres.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tp1_bec.R
import com.example.tp1_bec.pres.Singletons
import com.example.tp1_bec.pres.api.CryptoResp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CryptoListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = CryptoAdapter(listOf(), ::onClickedCrypto)

    private val layoutManager = LinearLayoutManager(context)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crypot_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.crypto_recyclerview)
        recyclerView.apply {
            layoutManager = this@CryptoListFragment.layoutManager
            adapter = this@CryptoListFragment.adapter
        }
        callApi()

        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        refresh.setOnRefreshListener {
            callApi()
            if (refresh.isRefreshing) refresh.isRefreshing = false
        }

    }
        fun callApi(){
            Singletons.cryptoApi.getCryptoList().enqueue(object: Callback<CryptoResp>{
                override fun onFailure(call: Call<CryptoResp>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<CryptoResp>, response: Response<CryptoResp>) {
                    if (response.isSuccessful && response.body() != null) {
                        val cryptoResp: CryptoResp = response.body()!!
                        adapter.updateList(cryptoResp.data)
                    }
                }
            })
        }


    private fun onClickedCrypto( crypto: Crypto) {
        findNavController().navigate(R.id.CryptoListFragementToCryptoDetailsFragement)
    }
}