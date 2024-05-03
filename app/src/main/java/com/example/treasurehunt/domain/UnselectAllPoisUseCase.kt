package com.example.treasurehunt.domain

import android.util.Log
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.PoiRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UnselectAllPoisUseCase @Inject constructor(val poirepository: PoiRepositoryInterface) {
    suspend operator fun invoke(): Flow<Result<Unit>> = flow {
        emit(Result.Loading())
        try {
            emit(Result.Success(poirepository.unselectAllPois()))
        } catch (e: Exception) {
            Log.e("UnselectAllPoisUseCase", "An unexpected error occured", e)
            emit(Result.Error(e.message ?: "An unexpected error occured"))
        }
    }
}