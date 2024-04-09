package com.example.treasurehunt.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    //@Singleton
    abstract fun bindPoiRepository(poiRepositoryImpl: PoiRepository): PoiRepositoryInterface

}

