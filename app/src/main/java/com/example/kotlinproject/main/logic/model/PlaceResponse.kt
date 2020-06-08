package com.example.kotlinproject.main.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author zt
 * @date 2020/6/8 15:22
 * @description 具体位置的实体类
 **/
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location, @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)