package com.vinayakgardi.coingraph.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vinayakgardi.coingraph.R
import com.vinayakgardi.coingraph.databinding.FragmentDetailsBinding
import com.vinayakgardi.coingraph.main.model.CryptoCurrency
import com.vinayakgardi.coingraph.main.utlities.Utilities


class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding

    private val coinItem: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        val data: CryptoCurrency? = coinItem.data

        setUpDetails(data!!)

        setUpWebView(data!!)

        setupAdditionalData(data!!)

        setButtonClickListener(data!!)




        return binding.root
    }

    private fun setupAdditionalData(data: CryptoCurrency) {
        val totalSupply = data.totalSupply
        val percentChange1y = data.quotes[0].percentChange1y
        val name = data.name
        val marketCap = data.quotes[0].marketCap
        val volume24h = data.quotes[0].volume24h
        val dominance = data.quotes[0].dominance

        binding.coinDominanceTextView.text = "Dominance : "+dominance.toString()
        binding.coinNameTextView.text = "Name : "+name.toString()
        binding.coinPercentChange1yTextView.text = "Percent Change 1 Y : "+percentChange1y.toString()+" %"
        binding.coinMarketcapTextView.text = "Market Cap : $"+marketCap.toString()
        binding.coinVolume24hTextView.text = "Volume 24h : $"+volume24h.toString()
        binding.coinTotalSupplyTextView.text = "Total Supply : $"+totalSupply.toString()
    }

    private fun setButtonClickListener(data: CryptoCurrency) {
        val fifteenMinutes = binding.button5
        val oneHour = binding.button4
        val fourHour = binding.button3
        val oneDay = binding.button2
        val oneWeek = binding.button1
        val oneMonth = binding.button

        val clickListener = View.OnClickListener {
            when (it.id) {
                fifteenMinutes.id -> loadChart(
                    it,
                    "15",
                    data,
                    oneHour,
                    fourHour,
                    oneDay,
                    oneWeek,
                    oneMonth
                )

                oneHour.id -> loadChart(
                    it,
                    "1H",
                    data,
                    fifteenMinutes,
                    fourHour,
                    oneDay,
                    oneWeek,
                    oneMonth
                )

                fourHour.id -> loadChart(
                    it,
                    "4H",
                    data,
                    oneHour,
                    fifteenMinutes,
                    oneDay,
                    oneWeek,
                    oneMonth
                )

                oneDay.id -> loadChart(
                    it,
                    "D",
                    data,
                    oneHour,
                    fourHour,
                    fifteenMinutes,
                    oneWeek,
                    oneMonth
                )

                oneWeek.id -> loadChart(
                    it,
                    "W",
                    data,
                    oneHour,
                    fourHour,
                    oneDay,
                    fifteenMinutes,
                    oneMonth
                )

                oneMonth.id -> loadChart(
                    it,
                    "M",
                    data,
                    oneHour,
                    fourHour,
                    oneDay,
                    oneWeek,
                    fifteenMinutes
                )
            }
        }
        fifteenMinutes.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
    }

    private fun loadChart(
        it: View?,
        s: String,
        data: CryptoCurrency,
        button1: AppCompatButton,
        button2: AppCompatButton,
        button3: AppCompatButton,
        button4: AppCompatButton,
        button5: AppCompatButton
    ) {

        it?.setBackgroundResource(R.drawable.active_button)
        disableButtons(button1, button2, button3, button4, button5)

        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerPaint(null)
        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + data.symbol + "/USD&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    private fun disableButtons(
        button1: AppCompatButton,
        button2: AppCompatButton,
        button3: AppCompatButton,
        button4: AppCompatButton,
        button5: AppCompatButton
    ) {
        button1.background = null
        button2.background = null
        button3.background = null
        button4.background = null
        button5.background = null
    }

    private fun setUpWebView(data: CryptoCurrency) {
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerPaint(null)
        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + data.symbol.toString() + "/USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en"
        )

    }

    private fun setUpDetails(data: CryptoCurrency) {
        binding.detailSymbolTextView.text = data.symbol // textview
        binding.detailPriceTextView.text =
            Utilities.roundToTwoDecimals(data.quotes[0]!!.price).toString() //

        Utilities.loadFromGlide(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png",
            binding.detailImageView,
            requireContext()
        )// symbol image

        binding.detailChangeTextView.text =
            Utilities.roundToTwoDecimals(data.quotes!![0].percentChange24h).toString()   // change

        if (data.quotes!![0].percentChange24h > 0) {
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
            context?.let { binding.detailChangeTextView.setTextColor(it.getColor(R.color.green)) }
        } else {
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
            context?.let {
                binding.detailChangeTextView.setTextColor(it.getColor(R.color.red))
            }
        }


    }
}
