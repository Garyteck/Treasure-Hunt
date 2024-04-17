package com.example.treasurehunt.data

import android.content.Context
import androidx.room.Room
import com.example.treasurehunt.data.database.PoiDB
import com.example.treasurehunt.data.remote.PoiNetWorkInterface
import com.example.treasurehunt.data.remote.PoiNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePoiDatabase(@ApplicationContext context: Context): PoiDB {
        return Room.databaseBuilder(
            context,
            PoiDB::class.java,
            "poi_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePoiDao(poiDB: PoiDB) = poiDB.poiDao()

    @Provides
    @Singleton
    fun provideNetworkInterface(): PoiNetWorkInterface {
        return PoiNetwork()
    }


}