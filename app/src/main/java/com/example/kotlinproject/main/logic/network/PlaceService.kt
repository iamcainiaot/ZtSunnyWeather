package com.example.kotlinproject.main.logic.network

import com.example.kotlinproject.main.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author zt
 * @date 2020/6/8 15:25
 * @description
 **/
interface PlaceService {
    @GET("v2/place?token=￥｛SunnyWeatherApplication.TOKEN｝&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}