package com.example.treasurehunt.data.remote

import com.example.treasurehunt.data.database.PoiEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface PoiNetWorkInterface {
    suspend fun getAllPois(): Flow<List<PoiDTO>>
}
class PoiNetwork : PoiNetWorkInterface {
    override suspend fun getAllPois(): Flow<List<PoiDTO>> = flow {
        emit(listOf(
            PoiDTO(1, "Poi 1", "MAd of KAffe", 55.686817, 12.532038, "https://www.example" +
                    ".com/image.jpg", false),
            PoiDTO(2, "Poi 2", "Bornehuset Stjernen", 55.689773, 12.534312, "https://www.example" +
                    ".com/image.jpg", false),
            PoiDTO(3, "Home", "KrpSV14", 55.688234, 12.533233, "https://www" +
                    ".example" +
                    ".com/image.jpg", false),
        )) // TODO replace with actual network call
    }
}


data class PoiDTO(
    val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val image: String,
    val isFound: Boolean = false
) {
    fun toPoiEntity() = PoiEntity(id, name, description, latitude, longitude, image, false)
}
