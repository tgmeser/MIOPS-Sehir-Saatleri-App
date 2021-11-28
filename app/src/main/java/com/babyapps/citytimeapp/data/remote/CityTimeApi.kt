package com.babyapps.citytimeapp.data.remote

import com.babyapps.citytimeapp.data.City
import retrofit2.Response
import retrofit2.http.GET

interface CityTimeApi {

    @GET("/cities")
    suspend fun getCities(): List<City>

}