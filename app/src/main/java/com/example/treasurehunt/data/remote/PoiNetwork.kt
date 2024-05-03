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
            PoiDTO(1, "Poi 1", "MAd of KAffe", 55.686817, 12.532038, "https://picsum.photos/200", false),
            PoiDTO(2, "Poi 2", "Bornehuset Stjernen", 55.689773, 12.534312, "https://picsum.photos/200", false),
            PoiDTO(3, "Sputnikkollegiet", "Botilbud på Frederiksberg", 55.6883755, 12.5320072, "https://picsum.photos/200", false),
            PoiDTO(4, "PureGym", "Sportkluc", 55.6901329, 12.5324712, "https://picsum.photos/200", false),
            PoiDTO(5, "Let's Play", "Cyberkaffe", 55.6894304, 12.5296443, "https://picsum.photos/200", false),
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
