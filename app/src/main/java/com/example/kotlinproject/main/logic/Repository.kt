package com.example.kotlinproject.main.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.kotlinproject.main.logic.model.Place
import com.example.kotlinproject.main.logic.model.Weather
import com.example.kotlinproject.main.logic.network.SunnyWeatherNetwork
import com.sunnyweather.android.logic.dao.PlaceDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * @author zt
 * @date 2020/6/8 16:03
 * @description 网络请求仓库层统一封装入口
 **/
object Repository {
    // 协程指定为Dispatchers.IO，让本操作运行在子线程中
    /**
     * 查找地区请求
     */
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    /**
     * 刷新天气信息请求
     */
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async { SunnyWeatherNetwork.getRealtimeWeather(lng, lat) }

            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}"
                                + "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    /**
     * 按照liveData()函数的参数接收标准定义的一个高阶函数，在fire()函数内部会先调用livaData()函数，
     * 然后在liveData()函数的代码块中统一进行了try&catch处理，并在try语句中调用传入的Lambda表达式中的代码，
     * 最终获取Lambda表达式的执行结果并调用emit()方法发送出去
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                // block函数的作用之，执行高阶函数传入的代码，或者是将参数初始化
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            /**
             * Note that this function suspends until the value is set on the [LiveData].
             * like [LiveData]'s method -- setValue(),to subscribe data changed
             */
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}