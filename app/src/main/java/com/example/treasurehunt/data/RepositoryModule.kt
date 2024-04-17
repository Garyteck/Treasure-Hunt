package com.example.treasurehunt.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    //@Singleton
    abstract fun bindPoiRepository(poiRepositoryImpl: PoiRepository): PoiRepositoryInterface

}

