package com.example.treasurehunt.domain

import android.util.Log
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.PoiRepositoryInterface
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPoisUseCase @Inject constructor(val poirepository: PoiRepositoryInterface) {
    suspend operator fun invoke() = flow {
        emit(Result.Loading())
        try {
            emitAll(poirepository.getAllPois().map { Result.Success(it) })
        } catch (e: Exception) {
            Log.e("GetAllPoisUseCase", "An unexpected error occured",e)
            emit(Result.Error(e.message ?: "An unexpected error occured"))
        }
    }
}