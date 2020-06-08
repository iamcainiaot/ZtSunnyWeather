package com.example.kotlinproject.main.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.kotlinproject.main.logic.model.Place
import com.example.kotlinproject.main.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

/**
 * @author zt
 * @date 2020/6/8 16:03
 * @description 网络请求仓库层统一封装入口
 **/
object Repository {
    // 协程指定为Dispatchers.IO，让本操作运行在子线程中
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        /**
         * Note that this function suspends until the value is set on the [LiveData].
         * like [LiveData]'s method -- setValue(),to subscribe data changed
         */
        emit(result)
    }
}