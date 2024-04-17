package com.example.treasurehunt.domain

import com.example.treasurehunt.Result
import com.example.treasurehunt.data.PoiRepositoryInterface
import com.example.treasurehunt.data.model.PoiItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SelectPoiUseCase @Inject constructor(
    private val poiRepositoryInterface: PoiRepositoryInterface
) {
    suspend operator fun invoke(poiItem: PoiItem) =
        flow {
            poiRepositoryInterface.updatePois(poiItem.copy(isFound = true))
            emit(poiItem.id)
        }
            .map { Result.Success(it) as Result<Int> }
            .catch { emit(Result.Error("Could not select Poi")) }
            .onStart { emit(Result.Loading()) }
}