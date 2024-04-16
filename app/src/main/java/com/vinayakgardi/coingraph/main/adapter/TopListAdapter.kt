package com.vinayakgardi.coingraph.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.vinayakgardi.coingraph.R
import com.vinayakgardi.coingraph.databinding.TopCurrencyLayoutBinding
import com.vinayakgardi.coingraph.main.utlities.Utilities
import com.vinayakgardi.coingraph.main.model.CryptoCurrency

class TopListAdapter(val context: Context , val topCryptoCurrencyList : List<CryptoCurrency>) : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
      var binding = TopCurrencyLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.top_currency_layout , parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topCryptoCurrencyList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentCoin = topCryptoCurrencyList[position]

        val currencyName = holder.itemView.findViewById<TextView>(R.id.topCurrencyNameTextView)
        currencyName.text = currentCoin.name

        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+currentCoin.id+".png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.itemView.findViewById(R.id.topCurrencyImageView))


        if(currentCoin.quotes[0].percentChange24h>0){
            holder.itemView.findViewById<TextView>(R.id.topCurrencyChangeTextView).setTextColor(context.getColor(R.color.green))
            holder.itemView.findViewById<TextView>(R.id.topCurrencyChangeTextView).text =
              "+ ${Utilities.roundToTwoDecimals(currentCoin.quotes[0].percentChange24h)} %"
        }else{
            holder.itemView.findViewById<TextView>(R.id.topCurrencyChangeTextView).setTextColor(context.getColor(R.color.red))
            holder.itemView.findViewById<TextView>(R.id.topCurrencyChangeTextView).text =
                "${Utilities.roundToTwoDecimals(currentCoin.quotes[0].percentChange24h)} %"
        }




    }
}