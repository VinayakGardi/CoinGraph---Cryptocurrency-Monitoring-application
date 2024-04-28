package com.vinayakgardi.coingraph.main.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vinayakgardi.coingraph.databinding.FragmentStatsBinding
import com.vinayakgardi.coingraph.main.adapter.MarketAdapter
import com.vinayakgardi.coingraph.main.model.CryptoCurrency
import com.vinayakgardi.coingraph.main.utlities.Utilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StatsFragment : Fragment() {

    lateinit var binding: FragmentStatsBinding
    lateinit var marketList: List<CryptoCurrency>
    lateinit var adapter: MarketAdapter
    lateinit var searchText: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentStatsBinding.inflate(layoutInflater)

        marketList = listOf()

        adapter = MarketAdapter(requireContext(), marketList, "Stat")
        binding.currencyRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val res = Utilities.loadDataFromApi()
            if (res.body() != null) {

                withContext(Dispatchers.Main) {
                    marketList = res.body()!!.data.cryptoCurrencyList
                    adapter.updateData(marketList)
                    binding.spinKitView.visibility = View.GONE
                }
            }
        }

        searchCoin()

        return binding.root
    }

    private fun searchCoin() {

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchText = p0.toString().lowercase()
                updateList()
            }

            override fun afterTextChanged(p0: Editable?) {
                searchText = p0.toString().lowercase()
                updateList()
            }


        })
    }

    private fun updateList() {
        val data = ArrayList<CryptoCurrency>()

        for (item in marketList) {
            val coinName = item.name.lowercase()
            val coinSymbol = item.symbol.lowercase()

            if (coinName.contains(searchText) || coinSymbol.contains(searchText)) {
                data.add(item)
            }
        }
        adapter.updateData(data)

    }


}