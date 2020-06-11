package com.example.kotlinproject.main.logic.model

/**
 * @author zt
 * @date 2020/6/10 10:46
 * @description 讲Realtime和Daily封装一层
 **/
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily) {

}