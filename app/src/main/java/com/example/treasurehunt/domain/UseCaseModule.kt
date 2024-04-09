package com.example.treasurehunt.domain

import com.example.treasurehunt.data.PoiRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun getAllPoisUseCase(poiRepository: PoiRepositoryInterface): GetAllPoisUseCase {
        return GetAllPoisUseCase(poiRepository)
    }
}