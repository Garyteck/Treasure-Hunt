package com.example.treasurehunt.domain
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.PoiRepositoryInterface
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.data.model.UserLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetClosestUnselectedPoiUseCase @Inject constructor(
    private val userLocation: Flow<UserLocation>,
    private val poiRepositoryInterface: PoiRepositoryInterface
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<Result<PoiItem?>> = userLocation.flatMapLatest {
            poiRepositoryInterface.getClosestUnselectedPoi(
                it.latitude,
                it.longitude
            ).map {
                Result.Success(it) as Result<PoiItem?>
            }

        }.catch { emit(Result.Error("Could not get closest Poi Item")) }
        .onStart { emit(Result.Loading()) }
}