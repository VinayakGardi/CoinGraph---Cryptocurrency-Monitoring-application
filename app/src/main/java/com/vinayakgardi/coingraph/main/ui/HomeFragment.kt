package com.vinayakgardi.coingraph.main.ui

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.vinayakgardi.coingraph.databinding.FragmentHomeBinding
import com.vinayakgardi.coingraph.main.adapter.TopGainLossPagerAdapter
import com.vinayakgardi.coingraph.main.adapter.TopListAdapter
import com.vinayakgardi.coingraph.main.model.CryptoCurrency
import com.vinayakgardi.coingraph.main.utlities.Utilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var topCurrencyList: ArrayList<CryptoCurrency>
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        checkAndHandleNetworkConnection(requireContext())



        return binding.root
    }

    private fun checkInternet(context: Context): Boolean {
        // check if connected to wifi or cellular data
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    fun checkAndHandleNetworkConnection(context: Context) {
        if (!checkInternet(context)) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Internet connection is unavailable. Please enable internet and try again.")
                .setPositiveButton("Enable") { _, _ ->
                    // Open system settings for internet connection
                    val intent =
                        context.packageManager.getLaunchIntentForPackage("com.android.settings")
                    if (intent != null) {
                        context.startActivity(intent)
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()
        } else {
            getTopCurrencyData()

            setupTabLayout()
        }
    }

    private fun setupTabLayout() {
        val adapter = TopGainLossPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.topGainIndicator.visibility = View.VISIBLE
                    binding.topLoseIndicator.visibility = View.GONE
                } else {
                    binding.topGainIndicator.visibility = View.GONE
                    binding.topLoseIndicator.visibility = View.VISIBLE
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.contentViewPager) { tab, position ->
            var title = if (position == 0) {
                "Top Gainers"
            } else {
                "Top Losers"
            }
            tab.text = title
        }.attach()
    }

    private fun getTopCurrencyData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = Utilities.loadDataFromApi()
            topCurrencyList = arrayListOf()


            for (i in 0..10) {
                topCurrencyList.add(res.body()!!.data.cryptoCurrencyList[i])
            }


            withContext(Dispatchers.Main) {
                binding.topCoinsRecyclerView.adapter =
                    TopListAdapter(requireContext(), topCurrencyList, "Home")
            }
            Log.d("APICALLING", "getTopCurrency: ${res.body()?.data?.cryptoCurrencyList}")
        }


    }


}