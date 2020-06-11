package com.example.kotlinproject.main.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author zt
 * @date 2020/6/10 10:31
 * @description 空气信息
 **/
data class RealtimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: Realtime)
    data class Realtime(
        val skycon: String,
        val temperature: Float, @SerializedName("air_quality") val airQuality: AirQuality
    )

    /**
     * 空气质量
     */
    data class AirQuality(val aqi: AQI)

    /**
     * xxx
     */
    data class AQI(val chn: Float)
}