package com.example.kotlinproject.main.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author zt
 * @date 2020/6/8 15:48
 * @description 统一网络数据访问入口
 **/
object SunnyWeatherNetwork {
    // 地点（区域、城市）相关得网络请求
    private val placeService = ServiceCreator.create(PlaceService::class.java)
    // 天气的网络请求
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    // 声明为挂起函数，当网络请求发起时，本函数会被挂起
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    // 天气的网络请求，当天的天气信息
    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()

    // 天气的网络请求，实时的天气信息
    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null)
                        continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}