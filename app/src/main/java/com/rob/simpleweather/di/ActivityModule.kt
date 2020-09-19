package com.rob.simpleweather.di

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.rob.simpleweather.main.favorites.OnFavoriteClicked
import com.rob.simpleweather.main.search.SearchCallbacks
import com.rob.simpleweather.main.settings.OnItemRemovedCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideAppCompatActivity(impl: Activity) : AppCompatActivity =
        impl as AppCompatActivity

    @Provides
    fun provideLayoutInflater(activity: Activity) = LayoutInflater.from(activity)

    @Provides
    fun provideFavoriteCallbacks(impl: Activity) : OnFavoriteClicked? =
        impl as? OnFavoriteClicked

    @Provides
    fun provideSearchCallbacks(impl: Activity) : SearchCallbacks? =
        impl as? SearchCallbacks

    @Provides
    fun provideItemRemovedCallbacks(impl: Activity) : OnItemRemovedCallback? =
        impl as? OnItemRemovedCallback
}
