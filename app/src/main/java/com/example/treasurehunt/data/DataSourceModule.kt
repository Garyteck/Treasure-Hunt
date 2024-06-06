package com.example.treasurehunt.data

import android.content.Context
import androidx.room.Room
import com.example.treasurehunt.data.database.PoiDB
import com.example.treasurehunt.data.model.UserLocation
import com.example.treasurehunt.data.remote.PoiNetWorkInterface
import com.example.treasurehunt.data.remote.PoiNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
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
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    @Provides
    @Singleton
    fun provideNetworkInterface(
        userLocationFlow: Flow<UserLocation>,
        httpClient: HttpClient
    ): PoiNetWorkInterface {
        return PoiNetwork(userLocationFlow, httpClient)
    }


}