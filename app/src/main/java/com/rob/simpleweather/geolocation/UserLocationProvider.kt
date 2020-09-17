package com.rob.simpleweather.geolocation

import androidx.lifecycle.map
import javax.inject.Inject

class UserLocationProvider @Inject constructor(
    private val locationManager: LocationManager,
    geocodingManager: GeocodingManager
) {

    val userLocation = locationManager.location
        .map { geocodingManager.reverseLocation(it) }

    fun canRequestLocation() = locationManager.isGooglePlayAvailable()

    fun requestUserLocation() = locationManager.getLastKnownLocation()
}
