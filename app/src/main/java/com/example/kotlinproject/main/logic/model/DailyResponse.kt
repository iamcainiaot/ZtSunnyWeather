package com.example.kotlinproject.main.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author zt
 * @date 2020/6/10 10:31
 * @description 每日的天气情况
 **/
data class DailyResponse(val status: String, val result: Result) {
    data class Result(val daily: Daily)
    data class Daily(
        val temperature: List<Temperature>,
        val skycon: List<Skycon>, @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class Temperature(val max: Float, val min: Float)
    data class Skycon(val value: String, val date: Date)
    data class LifeIndex(
        /**
         * 易发
         */
        val coldRisk: List<LifeDescription>,
        /**
         * 适宜
         */
        val carWashing: List<LifeDescription>,
        /**
         * 紫外线
         */
        val ultraviolet: List<LifeDescription>,
        /**
         * 舒适
         */
        val dressing: List<LifeDescription>
    )

    data class LifeDescription(val desc: String)
}