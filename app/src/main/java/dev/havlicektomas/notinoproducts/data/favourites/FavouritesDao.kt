package dev.havlicektomas.notinoproducts.data.favourites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: FavouritesEntity)

    @Query("DELETE FROM favouritesentity WHERE :id == id")
    suspend fun deleteItem(id: Long)

    @Query("SELECT * FROM favouritesentity")
    fun getFavourites(): Flow<List<FavouritesEntity>>
}