package com.rob.simpleweather.geolocation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import javax.inject.Inject

class LocationManager @Inject constructor(private val activity: AppCompatActivity) {

    private val fusedLocationClient
            by lazy { LocationServices.getFusedLocationProviderClient(activity) }

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val permissionContract =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.values.all { true }) {
                emitLastKnownLocation()
            }
        }

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                val lastLocation = result?.lastLocation ?: result?.locations?.firstOrNull()

                lastLocation?.let {
                    location.postValue(it)
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability?) {
                if(availability?.isLocationAvailable == true) {
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }
    }

    val location = MutableLiveData<Location>()

    fun isGooglePlayAvailable(): Boolean {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity) ==
                ConnectionResult.SUCCESS
    }

    fun getLastKnownLocation() {
        if (havePermissionsBeenGranted()) {
            emitLastKnownLocation()
        } else {
            permissionContract.launch(locationPermissions)
        }
    }

    private fun havePermissionsBeenGranted(): Boolean =
        locationPermissions.all { activity.isPermissionGranted(it) }

    private fun Activity.isPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun emitLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { l ->
                if (l != null) {
                    location.postValue(l)
                } else {
                    forceRequestLocationUpdate()
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun forceRequestLocationUpdate() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            interval = 20 * 1000
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }
}
