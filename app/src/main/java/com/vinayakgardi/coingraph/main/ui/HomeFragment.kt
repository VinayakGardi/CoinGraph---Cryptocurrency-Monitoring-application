package com.vinayakgardi.coingraph.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.vinayakgardi.coingraph.databinding.FragmentHomeBinding
import com.vinayakgardi.coingraph.main.adapter.TopGainLossPagerAdapter
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

        setupTabLayout()

        return binding.root
    }

    private fun setupTabLayout() {
        val adapter = TopGainLossPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0){
                    binding.topGainIndicator.visibility = View.VISIBLE
                    binding.topLoseIndicator.visibility = View.GONE
                }
                else{
                    binding.topGainIndicator.visibility = View.GONE
                    binding.topLoseIndicator.visibility = View.VISIBLE
                }
            }
        })

        TabLayoutMediator(binding.tabLayout , binding.contentViewPager){
            tab , position ->
            var title = if(position ==0 ){
                "Top Gainers"
            }else{
                "Top Losers"
            }
            tab.text = title
        }.attach()
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