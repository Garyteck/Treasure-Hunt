package com.example.treasurehunt.domain

import android.util.Log
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.PoiRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGameProgressUseCase @Inject constructor(val poirepository: PoiRepositoryInterface) {
    suspend operator fun invoke(): Flow<Result<Pair<Int,Int>>> = flow {
        emit(Result.Loading())
        try {
            emitAll(poirepository.getGameProgress().map { Result.Success(it) } )
        } catch (e: Exception) {
            Log.e("UnselectAllPoisUseCase", "An unexpected error occured", e)
            emit(Result.Error(e.message ?: "An unexpected error occured"))
        }
    }
}