package com.example.kotlinproject.main.application

import android.app.Application
import android.content.Context

/**
 * @author zt
 * @date 2020/6/8 15:17
 * @description 应用类
 **/
class SunnyWeatherApplication : Application() {

    companion object {
        const val TOKEN_CAI_YUN = "0LxDISdNAoRIil9z"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
