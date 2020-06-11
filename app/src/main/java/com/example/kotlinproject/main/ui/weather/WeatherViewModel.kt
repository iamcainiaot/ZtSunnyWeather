package com.example.kotlinproject.main.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.kotlinproject.main.logic.Repository
import com.example.kotlinproject.main.logic.model.Location

/**
 * @author zt
 * @date 2020/6/10 11:26
 * @description
 **/
class WeatherViewModel : ViewModel() {
    private val locationLivaData = MutableLiveData<Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLivaData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLivaData.value = Location(lng, lat)
    }

}