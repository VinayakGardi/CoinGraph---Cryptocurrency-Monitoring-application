package com.vinayakgardi.coingraph.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vinayakgardi.coingraph.R
import com.vinayakgardi.coingraph.databinding.CurrencyItemLayoutBinding
import com.vinayakgardi.coingraph.main.utlities.Utilities
import com.vinayakgardi.coingraph.main.model.CryptoCurrency
import com.vinayakgardi.coingraph.main.ui.HomeFragmentDirections
import com.vinayakgardi.coingraph.main.ui.SavedFragmentDirections
import com.vinayakgardi.coingraph.main.ui.StatsFragment
import com.vinayakgardi.coingraph.main.ui.StatsFragmentDirections

class MarketAdapter(val context: Context, var marketList: List<CryptoCurrency> , var type : String) :
    RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = CurrencyItemLayoutBinding.bind(itemView)
    }

    fun updateData( updateData : List<CryptoCurrency>){
        marketList = updateData
        notifyDataSetChanged()

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


        // open the detail view


        holder.itemView.setOnClickListener {
            if (type == "Home") {
                findNavController(it).navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment2(coin)
            )
        }
            else if(type == "Stat"){
                findNavController(it).navigate(
                    StatsFragmentDirections.actionStatsFragmentToDetailFragment(coin)
                )
            }
            else{
                findNavController(it).navigate(
                    SavedFragmentDirections.actionSavedFragmentToDetailFragment(coin)
                )
            }
        }
    }
}