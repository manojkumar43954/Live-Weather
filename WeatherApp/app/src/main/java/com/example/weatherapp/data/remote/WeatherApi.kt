package com.example.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") appId: String,
    ) : WeatherDataDto

    @GET("/geo/1.0/direct")
    suspend fun getWeatherDataByCityName(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("appid") appId: String,
    ) : List<CityDataDto>
}


