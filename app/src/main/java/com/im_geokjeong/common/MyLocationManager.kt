package com.im_geokjeong.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.location.Location

@SuppressLint("MissingPermission")
object MyLocationManager {

    fun getLocation(lm : LocationManager): Location? {
        return lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }
    fun getLocationManager(context: Context): LocationManager {
        return context.getSystemService(LOCATION_SERVICE) as LocationManager
    }
}