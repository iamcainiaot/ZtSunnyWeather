package com.example.kotlinproject.main.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author zt
 * @date 2020/6/8 15:29
 * @description
 **/
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    // 泛型的实化   inline修饰方法，reified修饰泛型 
    inline fun <reified T> create(): T = create(T::class.java)
}