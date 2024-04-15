package com.example.treasurehunt.domain

import android.util.Log
import com.example.treasurehunt.LocationUtils
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.PoiRepositoryInterface
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.data.model.UserLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetDistanceToClosestUnselectedPoi @Inject constructor(
    private val locationFlow: Flow<UserLocation>,
    private val poiRepositoryInterface: PoiRepositoryInterface
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<Result<Double>> = locationFlow
        .flatMapLatest { userLocation ->
            Log.e("GetDistance", "flatmaplatest location: $userLocation")
            poiRepositoryInterface.getClosestUnselectedPoi(
                userLocation.latitude,
                userLocation.longitude
            )
                .filterIsInstance<PoiItem>()
                .map { poi ->
                    Result.Success(
                        LocationUtils.calculateDistance(
                            userLocation.latitude,
                            userLocation.longitude,
                            poi.latitude,
                            poi.longitude
                        )
                    ) as Result<Double>
                }.also {
                    Log.e("GetDistance", "SendValue: $it")
                }

        }.onEach {
            Log.e("GetDistance", "OnEach: $it")
        }
        .catch { emit(Result.Error("fail to calculate distance to closest unselected poi")) }
        .onStart { emit(Result.Loading()) }
}
