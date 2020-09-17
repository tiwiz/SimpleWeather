package com.rob.simpleweather.geolocation

import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import javax.inject.Inject

class GeocodingManager @Inject constructor(activity: AppCompatActivity) {

    private val geocoder by lazy { Geocoder(activity, Locale.ITALIAN) }

    fun reverseLocation(location: Location): String? =
        geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .firstOrNull()
            ?.locality
}
