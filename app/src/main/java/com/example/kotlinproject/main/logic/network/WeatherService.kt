package com.example.kotlinproject.main.logic.network

import com.example.kotlinproject.main.application.SunnyWeatherApplication
import com.example.kotlinproject.main.logic.model.DailyResponse
import com.example.kotlinproject.main.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author zt
 * @date 2020/6/10 10:48
 * @description 获取天气接口
 **/
interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN_CAI_YUN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN_CAI_YUN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}