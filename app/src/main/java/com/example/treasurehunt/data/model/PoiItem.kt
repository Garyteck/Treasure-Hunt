package com.example.treasurehunt.data.model

import com.example.treasurehunt.data.database.PoiEntity
import com.google.android.gms.maps.model.LatLng

data class PoiItem(
    val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val image: String,
    val isFound: Boolean = false
) {
    fun toPoiEntity() = PoiEntity(id, name, description, latitude, longitude, image, isFound)
    fun toLatLng() = LatLng(latitude, longitude)
}
