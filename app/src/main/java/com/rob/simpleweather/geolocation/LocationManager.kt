package com.rob.simpleweather.geolocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class LocationManager @Inject constructor(private val activity: AppCompatActivity) {

    private val fusedLocationClient
            by lazy { LocationServices.getFusedLocationProviderClient(activity) }

    private val locationPermission = Manifest.permission.ACCESS_COARSE_LOCATION

    private val permissionContract = activity.registerForActivityResult(RequestPermission()) {
        if (it) {
            emitLastKnownLocation()
        }
    }

    val location = MutableLiveData<Location>()

    fun isGooglePlayAvailable(): Boolean {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity) ==
                ConnectionResult.SUCCESS
    }

    fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                activity,
                locationPermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            emitLastKnownLocation()
        } else {
            permissionContract.launch(locationPermission)
        }
    }

    @SuppressLint("MissingPermission")
    private fun emitLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { l -> location.postValue(l) }
    }
}
