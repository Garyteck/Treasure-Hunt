package com.example.treasurehunt.data

import com.example.treasurehunt.data.database.PoiDao
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.data.remote.PoiNetWorkInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface PoiRepositoryInterface {
    suspend fun getAllPois(): Flow<List<PoiItem>>
    suspend fun getPoiById(id: Int): Flow<PoiItem>
    suspend fun updatePois(vararg poi: PoiItem)
}

class PoiRepository @Inject constructor(private val poiDao: PoiDao, private val poiNetwork:
        PoiNetWorkInterface) :
    PoiRepositoryInterface {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllPois(): Flow<List<PoiItem>> {
        // make a network call and save the data to the db and return the data from the db
        return poiDao.getAllPois()
            .flatMapConcat { pois ->
                if (pois.isEmpty()) {
                    poiNetwork.getAllPois()
                        .map { dtos ->
                            dtos.map { it.toPoiEntity() }
                        }.flatMapConcat { entities ->
                            poiDao.insertPois(*entities.toTypedArray())
                            poiDao.getAllPois()
                        }
                } else {
                    poiDao.getAllPois()
                }
            }.map { entities ->
                entities.map { it.toPoiItem() }
            }
    }

    override suspend fun getPoiById(id: Int): Flow<PoiItem> {
        return poiDao.getById(id).map { it.toPoiItem() }
    }

    override suspend fun updatePois(vararg poi: PoiItem) {
        poiDao.updatePoi(*poi.map { it.toPoiEntity() }.toTypedArray())
    }


}


