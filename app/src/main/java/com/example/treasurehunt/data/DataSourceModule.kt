package com.example.treasurehunt.data

import android.content.Context
import androidx.room.Room
import com.example.treasurehunt.data.database.PoiDB
import com.example.treasurehunt.data.remote.PoiNetWorkInterface
import com.example.treasurehunt.data.remote.PoiNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

    @Provides
    fun providePoiDatabase(@ApplicationContext context: Context): PoiDB {
        return Room.databaseBuilder(
            context,
            PoiDB::class.java,
            "poi_database"
        ).build()
    }

    @Provides
    fun providePoiDao(poiDB: PoiDB) = poiDB.poiDao()

    @Provides
    fun provideNetworkInterface(): PoiNetWorkInterface {
        return PoiNetwork()
    }


}