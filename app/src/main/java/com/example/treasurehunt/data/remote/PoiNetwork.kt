package com.example.treasurehunt.data.remote

import com.example.treasurehunt.BuildConfig
import com.example.treasurehunt.data.database.PoiEntity
import com.example.treasurehunt.data.model.UserLocation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable

interface PoiNetWorkInterface {
    suspend fun getAllPois(): Flow<List<PoiDTO>>
}

class PoiNetwork(private val userLocation: Flow<UserLocation>, private val httpClient: HttpClient) :
    PoiNetWorkInterface {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllPois(): Flow<List<PoiDTO>> = flow {

        userLocation.collect { userLocation ->
            emit(httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "maps.googleapis.com"
                    path("maps", "api", "place", "nearbysearch", "json")
                    parameter("location", "${userLocation.latitude},${userLocation.longitude}")
                    parameter("radius", "100")
                    parameter("type", "point_of_interest")
                    parameter("key", BuildConfig.MAPS_API_KEY)
                }
            }.body<PoiResponse>().results)

        }
    }
}


@Serializable
data class PoiResponse(val results: List<PoiDTO>)
@Serializable
data class Geometry(
    val location: PoiLocation
)
@Serializable
data class PoiLocation(
    val lat: Double,
    val lng: Double
)

@Serializable
data class PoiDTO(
    val business_status: String,
    val geometry: Geometry,
    val place_id: String,
    val name: String,
    val vicinity: String,
) {
    fun toPoiEntity() = PoiEntity(
        0,
        name,
        vicinity,
        geometry.location.lat,
        geometry.location.lng,
        "",
        false
    )
}
