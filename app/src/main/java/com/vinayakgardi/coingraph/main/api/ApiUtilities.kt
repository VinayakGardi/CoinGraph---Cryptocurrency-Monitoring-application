package com.vinayakgardi.coingraph.main.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {

    fun getInstance() : Retrofit{

        val instance = Retrofit.Builder()
            .baseUrl("https://api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return instance
    }

}

//https://s2.coinmarketcap.com/static/img/coins/64x64/" + imageID + .png   // image loading website