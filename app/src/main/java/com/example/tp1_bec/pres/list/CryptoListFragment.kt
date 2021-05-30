package com.example.tp1_bec.pres.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tp1_bec.R
import com.example.tp1_bec.pres.Singletons
import com.example.tp1_bec.pres.api.CryptoResp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CryptoListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = CryptoAdapter(listOf(), ::onClickedCrypto)

   // val sharedPref = activity?.getSharedPreferences("app", Context.MODE_PRIVATE)


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
            layoutManager = LinearLayoutManager(context)
            adapter = this@CryptoListFragment.adapter
        }
        callApi()
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        refresh.setOnRefreshListener {
            callApi()

            if (refresh.isRefreshing) refresh.isRefreshing = false
        }
    }

    fun getListFromCache(): List<Crypto> {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref?.getString("LIST", null)
        if (json == null){
            return emptyList()
        } else {
            val type = object :TypeToken<List<Crypto>>(){}.type//converting the json to list
            return gson.fromJson(json,type)//returning the list
        }
   }


    private fun saveListIntoCache(cryptoList: List<Crypto>) {
        var gson = Gson()
        var json :String = gson.toJson(cryptoList)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("LIST", json)
            apply()
        }

    }

    private fun showList(cryptoList: List<Crypto>) {
        adapter.updateList(cryptoList)
    }

    fun callApi(){
        Singletons.cryptoApi.getCryptoList().enqueue(object: Callback<CryptoResp>{
            override fun onFailure(call: Call<CryptoResp>, t: Throwable) {
                val list: List<Crypto> = getListFromCache()
                if (list.isEmpty()){
                    callApi()
                } else {
                    showList(list)
                }
            }

            override fun onResponse(call: Call<CryptoResp>, response: Response<CryptoResp>) {
                if (response.isSuccessful && response.body() != null) {
                    val cryptoResp: CryptoResp = response.body()!!
                    saveListIntoCache(cryptoResp.data)
                    showList(cryptoResp.data)
                }
            }
        })
    }

    private fun onClickedCrypto(name:String) {
        findNavController().navigate(R.id.CryptoListFragementToCryptoDetailsFragement, bundleOf(
            "nameCrypto" to name
        ))
    }
}