package com.example.tp1_bec.pres.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_bec.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CryptoListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = CryptoAdapter(listOf())
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

        val cryptoList : ArrayList<Crypto> = arrayListOf<Crypto>().apply {
            add(Crypto("Bitcoin"))
            add(Crypto("Etherum"))
            add(Crypto("Dogecoin"))
            add(Crypto("BinanceCoin"))
            add(Crypto("Ripple"))
        }

        adapter.updateList(cryptoList)
    }
}