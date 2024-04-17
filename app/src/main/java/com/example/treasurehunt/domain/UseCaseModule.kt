package com.example.treasurehunt.domain

import com.example.treasurehunt.data.PoiRepositoryInterface
import com.example.treasurehunt.data.model.UserLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun getAllPoisUseCase(poiRepository: PoiRepositoryInterface): GetAllPoisUseCase {
        return GetAllPoisUseCase(poiRepository)
    }

    @Provides
    fun getDistanceToClosestUnselectedPoi(
        locationFlow: Flow<UserLocation>, poiRepositoryInterface: PoiRepositoryInterface
    ): GetDistanceToClosestUnselectedPoi {
        return GetDistanceToClosestUnselectedPoi(locationFlow, poiRepositoryInterface)
    }

    @Provides
    fun getDirectionToClosestUnselectedPoi(
        locationFlow: Flow<UserLocation>,
        screenOrientationFlow: Flow<Float>,
        poiRepositoryInterface: PoiRepositoryInterface
    ): GetDirectionToClosestUnselectedPoi {
        return GetDirectionToClosestUnselectedPoi(
            locationFlow, screenOrientationFlow, poiRepositoryInterface
        )
    }

    @Provides
    fun getClosestUnselectedPoiUseCase(
        locationFlow: Flow<UserLocation>,
        poiRepositoryInterface: PoiRepositoryInterface
    ): GetClosestUnselectedPoiUseCase {
        return GetClosestUnselectedPoiUseCase(locationFlow, poiRepositoryInterface)
    }

    @Provides
    fun getTogglePoiUseCase(
                            poiRepository: PoiRepositoryInterface) : SelectPoiUseCase {
        return SelectPoiUseCase(poiRepository)
    }
}