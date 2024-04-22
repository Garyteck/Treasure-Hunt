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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.math.abs

class GetDirectionToClosestUnselectedPoi
@Inject constructor(
    private val userLocationFlow: Flow<UserLocation>,
    private val screenOrientation: Flow<Float>,
    private val poiRepositoryInterface: PoiRepositoryInterface
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<Result<Double?>> =

        combine(userLocationFlow.flatMapLatest { userLocation ->
            poiRepositoryInterface.getClosestUnselectedPoi(
                userLocation.latitude,
                userLocation.latitude
            )
                .filterIsInstance<PoiItem>()
                .map { Pair(userLocation, it) }
        }, screenOrientation) { userLocationClosestPoiPair, orientation ->
            /*Log.e(
                "GetDirectionToClosestUnselectedPoi",
                "UserLocation: ${userLocationClosestPoiPair}"
            )
            Log.e("GetDirectionToClosestUnselectedPoi", "Orientation: $orientation")*/
            userLocationClosestPoiPair.first
            Result.Success(
                abs(
                    (LocationUtils.calculateDirection(
                        userLocationClosestPoiPair.first.latitude,
                        userLocationClosestPoiPair.first.longitude,
                        userLocationClosestPoiPair.second.latitude,
                        userLocationClosestPoiPair.second.longitude
                    ) - orientation)
                ).apply {
                    //Log.e("GetDirectionToClosestUnselectedPoi", "Direction: $this")
                }

            ) as Result<Double?>
        }.onEach {
            //Log.e("GetDistance", "OnEach: $it")
        }.catch { emit(Result.Error("fail to calculate direction to closest unselected poi")) }
            .onStart { emit(Result.Loading()) }
}
