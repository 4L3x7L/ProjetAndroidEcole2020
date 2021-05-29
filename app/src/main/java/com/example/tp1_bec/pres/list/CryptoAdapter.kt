package com.example.tp1_bec.pres.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_bec.R


class CryptoAdapter(private var dataSet: List<Crypto>, val listener: ((Crypto)-> Unit)? = null) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val trigram: TextView
        val priceUsd: TextView
        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.crypto_name)
            trigram = view.findViewById(R.id.crypto_trigram)
            priceUsd = view.findViewById(R.id.crypto_price)

        }
    }

    fun updateList(list: List<Crypto>){
        dataSet = list
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.crypto_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val crypto: Crypto = dataSet[position]
        viewHolder.name.text = crypto.name
        viewHolder.trigram.text = crypto.symbol
        viewHolder.priceUsd.text = "%.2f".format(crypto.priceUsd.toDouble())
        viewHolder.itemView.setOnClickListener {
            listener?.invoke(crypto)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
