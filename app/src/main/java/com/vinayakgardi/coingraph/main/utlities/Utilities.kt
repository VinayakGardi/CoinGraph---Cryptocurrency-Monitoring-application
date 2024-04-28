package com.vinayakgardi.coingraph.main.utlities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vinayakgardi.coingraph.R
import com.vinayakgardi.coingraph.main.api.ApiInterface
import com.vinayakgardi.coingraph.main.api.ApiUtilities
import com.vinayakgardi.coingraph.main.model.DataModel
import retrofit2.Response

object Utilities {

    fun roundToTwoDecimals(number: Double): Double {
        return String.format("%.6f", number).toDouble()
    }

    fun loadFromGlide(source : String  , View : ImageView , context : Context){
        Glide.with(context).load(source)
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(View)
    }

    suspend fun loadDataFromApi(): Response<DataModel> {
        val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getData()
        return res
    }

    
}