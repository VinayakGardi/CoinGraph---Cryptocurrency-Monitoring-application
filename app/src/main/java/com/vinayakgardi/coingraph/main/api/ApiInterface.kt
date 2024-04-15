package com.vinayakgardi.coingraph.main.api

import com.vinayakgardi.coingraph.main.model.DataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getData() : Response<DataModel>
}