package com.vinayakgardi.coingraph.main.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vinayakgardi.coingraph.databinding.FragmentSavedBinding
import com.vinayakgardi.coingraph.main.adapter.MarketAdapter
import com.vinayakgardi.coingraph.main.model.CryptoCurrency
import com.vinayakgardi.coingraph.main.utlities.Utilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedList: ArrayList<String>
    private lateinit var savedListItem: ArrayList<CryptoCurrency>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(layoutInflater)


        readData()

        lifecycleScope.launch(Dispatchers.IO) {
            val res = Utilities.loadDataFromApi()
            if(res.body()!=null){
                withContext(Dispatchers.Main){
                    savedListItem = ArrayList()
                    savedListItem.clear()

                    for(savedItem in savedList){
                        for(item in res.body()!!.data.cryptoCurrencyList){
                            if(savedItem == item.symbol){
                                savedListItem.add(item)
                            }
                        }
                    }

                    binding.spinKitView.visibility = View.GONE

                    binding.watchlistRecyclerView.adapter = MarketAdapter(requireContext() , savedListItem , "Saved")
                }
            }
        }

        return binding.root
    }

    private fun readData() {
        val sharedPreferences =
            requireContext().getSharedPreferences("savedList", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("savedList", ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        savedList = gson.fromJson(json, type)
    }


}