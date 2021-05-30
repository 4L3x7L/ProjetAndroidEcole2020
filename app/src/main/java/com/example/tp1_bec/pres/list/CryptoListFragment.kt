package com.example.tp1_bec.pres.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
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
    private val viewModel: CryptoListViewModel by viewModels()
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

        viewModel.cryptoList.observe(viewLifecycleOwner, Observer { cryptoModel ->
            if (cryptoModel is CryptoSuccess){
                saveListIntoCache(cryptoModel.cryptoList)
                showList(cryptoModel.cryptoList)
            } else if (cryptoModel is CryptoError){
                showList(getListFromCache())
                Toast.makeText(activity, "Vous etes offline", Toast.LENGTH_LONG).show()
            }
        })

        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        refresh.setOnRefreshListener {
            viewModel.callApi()
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

    private fun onClickedCrypto(name:String) {
        findNavController().navigate(R.id.CryptoListFragementToCryptoDetailsFragement, bundleOf(
            "nameCrypto" to name
        ))
    }
}