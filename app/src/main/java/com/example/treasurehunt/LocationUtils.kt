package com.example.treasurehunt

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

object LocationUtils {

    /* minimum distance in meters to enable Poi selection*/
    const val DISTANCE_THRESHOLD: Double = 5.0

    private const val EARTH_RADIUS = 6371e3 // meters

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2:Double): Double {
        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)
        val dLatRad = Math.toRadians(lat2 - lat1)
        val dLonRad = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLatRad / 2) * Math.sin(dLatRad / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(dLonRad / 2) * Math.sin(dLonRad / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        val distance = EARTH_RADIUS * c
        return distance
    }

    fun calculateDirection(latFrom: Double, lonFrom: Double, latTo: Double, lonTo: Double): Double {
        val dLon = Math.toRadians(lonTo - lonFrom)

        val y = Math.sin(dLon) * Math.cos(Math.toRadians(latTo))
        val x = Math.cos(Math.toRadians(latFrom)) * Math.sin(Math.toRadians(latTo)) - Math.sin(Math.toRadians(latFrom)) * Math.cos(Math.toRadians(latTo)) * Math.cos(dLon)

        val brng = Math.atan2(y, x)

        val degrees = Math.toDegrees(brng)
        var adjusted = (degrees + 360.0) % 360.0


        val targetLatLng = SphericalUtil.computeOffset(LatLng(latFrom, lonFrom), 1.0, adjusted)
        if(calculateDistance(targetLatLng.latitude, targetLatLng.longitude, latTo, lonTo) > calculateDistance(latFrom, lonFrom, latTo, lonTo)) {
            adjusted += 180.0
        }
        return adjusted % 360  // Clockwise


    }



}