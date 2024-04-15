package com.example.treasurehunt

object LocationUtils {

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

        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)
        val dLonRad = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLonRad / 2) * Math.sin(dLonRad / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(Math.PI * dLonRad / 360)

        //val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        // calculate initial direction in radians
        val direction = Math.atan2(
            Math.sin(dLonRad) * Math.cos(lat2Rad),
            Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(dLonRad)
        )

        // convert direction to degrees (optional) and normalize to 0-360
        return Math.toDegrees(direction) + 360.0 % 360.0
    }
}