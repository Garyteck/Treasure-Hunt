package com.example.treasurehunt

import android.util.Log

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
    fun calculateDirection(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val dLon = Math.toRadians(lon2 - lon1)

        val y = Math.sin(dLon) * Math.cos(Math.toRadians(lat2))
        val x = Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) - Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(dLon)

        val brng = Math.atan2(y, x)

        val degrees = Math.toDegrees(brng)
        val adjusted = (degrees + 360.0) % 360.0

        // Adjust for clockwise or counter-clockwise based on your need
       // return 360.0 - adjusted // Counter-clockwise

        Log.e("LocationUtils", "Adjusted: $adjusted")
        return adjusted // Clockwise
    }
}