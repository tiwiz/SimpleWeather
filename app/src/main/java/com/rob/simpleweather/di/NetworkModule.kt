package com.rob.simpleweather.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rob.simpleweather.repository.WeatherApi
import com.rob.simpleweather.repository.WeatherApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(context: Application): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(WeatherApiKeyInterceptor())
            .build()

    @Provides
    fun provideMediaType(): MediaType =
        "application/json".toMediaType()

    @ExperimentalSerializationApi
    @Provides
    fun provideJsonConverterFactory(mediaType: MediaType): Converter.Factory =
        Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(mediaType)

    @ExperimentalSerializationApi
    @Provides
    fun provideApi(client: OkHttpClient, factory: Converter.Factory): WeatherApi =
        Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(WeatherApi::class.java)
}
