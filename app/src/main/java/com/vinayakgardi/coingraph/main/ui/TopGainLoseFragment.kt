package com.vinayakgardi.coingraph.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vinayakgardi.coingraph.databinding.FragmentTopGainLoseBinding
import com.vinayakgardi.coingraph.main.adapter.MarketAdapter
import com.vinayakgardi.coingraph.main.api.ApiInterface
import com.vinayakgardi.coingraph.main.api.ApiUtilities
import com.vinayakgardi.coingraph.main.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections


class TopGainLoseFragment : Fragment() {

    lateinit var binding: FragmentTopGainLoseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopGainLoseBinding.inflate(layoutInflater)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {
        val position = requireArguments().getInt("position")
        lifecycleScope.launch(Dispatchers.IO) {
            var res = ApiUtilities.getInstance().create(ApiInterface::class.java).getData().body()
            if (res != null) {

                withContext(Dispatchers.Main) {
                    val items = res!!.data.cryptoCurrencyList

                    Collections.sort(items) { o1, o2 ->
                        (o2.quotes[0]!!.percentChange24h.toInt())
                            .compareTo(o1.quotes[0]!!.percentChange24h.toInt())
                    }

                    binding.spinKitView.visibility = View.GONE

                    val list = ArrayList<CryptoCurrency>()

                    if (position == 1) { // loss
                        list.clear()
                        for (i in 0..19) {
                            list.add(items[items.size - 1 - i])
                        }
                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list ,"Home")

                    } else {  // gain
                        list.clear()
                        for (i in 0..19) {
                            list.add(items[i])
                        }
                        binding.topGainLoseRecyclerView.adapter =
                            MarketAdapter(requireContext(), list , "Home")

                    }


                }
            }
        }
    }
}


