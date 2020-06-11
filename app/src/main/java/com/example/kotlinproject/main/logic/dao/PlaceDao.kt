package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.kotlinproject.main.application.SunnyWeatherApplication
import com.google.gson.Gson
import com.example.kotlinproject.main.logic.model.Place

/**
 * @author taozhu
 * @date 2020/6/11 10:31
 * @description 记录选中的城市相关Dao类
 **/
object PlaceDao {

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}