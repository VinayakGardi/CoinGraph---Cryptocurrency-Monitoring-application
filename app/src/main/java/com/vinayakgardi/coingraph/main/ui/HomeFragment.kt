package com.vinayakgardi.coingraph.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.vinayakgardi.coingraph.R
import com.vinayakgardi.coingraph.databinding.FragmentHomeBinding
import com.vinayakgardi.coingraph.main.adapter.TopListAdapter
import com.vinayakgardi.coingraph.main.api.ApiInterface
import com.vinayakgardi.coingraph.main.api.ApiUtilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getTopCurrencyData()

        return binding.root
    }

    private fun getTopCurrencyData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getData()

            withContext(Dispatchers.Main){
                binding.topCoinsRecyclerView.adapter = TopListAdapter(requireContext() , res.body()!!.data.cryptoCurrencyList)
            }
            Log.d("APICALLING", "getTopCurrency: ${res.body()?.data?.cryptoCurrencyList}")
        }


    }


}