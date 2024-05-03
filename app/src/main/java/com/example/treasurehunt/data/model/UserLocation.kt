package com.example.treasurehunt.data.model

import com.google.android.gms.maps.model.LatLng

data class UserLocation(val latitude: Double, val longitude: Double)

fun UserLocation.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}
