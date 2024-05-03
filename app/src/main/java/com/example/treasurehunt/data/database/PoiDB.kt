package com.example.treasurehunt.data.database
// this is the room Db File
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.treasurehunt.data.model.PoiItem
import kotlinx.coroutines.flow.Flow

@Database(entities = [PoiEntity::class], version = 1, exportSchema = false)
// provide room schema location
abstract class PoiDB : RoomDatabase() {
    abstract fun poiDao(): PoiDao
}

@Entity
data class PoiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val image: String,
    val isSelected: Boolean = false
) {
    fun toPoiItem(): PoiItem {
        return PoiItem(id, name, description, latitude, longitude, image, isSelected)
    }
}
@Dao
abstract class PoiDao{
    @Query("SELECT * FROM poientity")
    abstract fun getAllPois(): Flow<List<PoiEntity>>

    @Query("SELECT * FROM poientity WHERE id =:id")
    abstract fun getById(id : Int): Flow<PoiEntity>

    @Insert
    abstract suspend fun insertPois(vararg poi: PoiEntity)

    @Update
    abstract suspend fun updatePoi(vararg poi: PoiEntity)

    @Query("SELECT * FROM poientity WHERE isSelected = 1")
    abstract fun getSelectedPois(): Flow<List<PoiEntity>>
    @Query("SELECT * FROM poientity WHERE isSelected = 0")
    abstract fun getNonSelectedPois(): Flow<List<PoiEntity>>

    @Query("SELECT * FROM poientity WHERE isSelected = 0")
    abstract fun getNonSelectedPoisValue(): List<PoiEntity>

    @Query("UPDATE poientity SET isSelected = 0")
    abstract fun unselectAllPois()
}


