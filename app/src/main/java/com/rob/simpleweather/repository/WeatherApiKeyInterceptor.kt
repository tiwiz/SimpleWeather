package com.rob.simpleweather.repository

import com.rob.simpleweather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class WeatherApiKeyInterceptor(private val key: String = BuildConfig.WEATHER_API_KEY) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url
            .newBuilder()
            .addQueryParameter("key", key)
            .build()

        return request.newBuilder().url(newUrl).build().let {
            chain.proceed(it)
        }
    }
}
