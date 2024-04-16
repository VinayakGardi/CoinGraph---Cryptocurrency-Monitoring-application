package com.vinayakgardi.coingraph.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinayakgardi.coingraph.R
import com.vinayakgardi.coingraph.databinding.CurrencyItemLayoutBinding
import com.vinayakgardi.coingraph.main.utlities.Utilities
import com.vinayakgardi.coingraph.main.model.CryptoCurrency

class MarketAdapter(val context: Context, var marketList: List<CryptoCurrency>) :
    RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = CurrencyItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_item_layout , parent , false))
    }

    override fun getItemCount(): Int {
        return marketList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = marketList[position]

        holder.binding.currencyNameTextView.text = coin.name   // name

//        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+coin.id+".png")
//            .thumbnail(Glide.with(context).load(R.drawable.spinner))
//            .into(holder.itemView.findViewById(R.id.currencyImageView))

        Utilities.loadFromGlide("https://s2.coinmarketcap.com/static/img/coins/64x64/"+coin.id+".png" , holder.binding.currencyImageView , context)// symbol image

        holder.binding.currencyChangeTextView.text = Utilities.roundToTwoDecimals(coin.quotes!![0].percentChange24h).toString()   // change

        if(coin.quotes!![0].percentChange24h>0){
             holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_up)
             holder.binding.currencyChangeTextView.setTextColor(context.getColor(R.color.green))
        }
        else{
            holder.binding.currencyChangeImageView.setImageResource(R.drawable.ic_caret_down)
            holder.binding.currencyChangeTextView.setTextColor(context.getColor(R.color.red))


        }
        holder.binding.currencyPriceTextView.text = Utilities.roundToTwoDecimals(coin.quotes!![0].price).toString()         // price

        holder.binding.currencySymbolTextView.text = coin.symbol // symbol in string

        Utilities.loadFromGlide("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + coin.id + ".png" , holder.binding.currencyChartImageView , context)


    }
}