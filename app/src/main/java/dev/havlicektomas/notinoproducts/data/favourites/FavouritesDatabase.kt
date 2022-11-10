package dev.havlicektomas.notinoproducts.data.favourites

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Database(
    entities = [FavouritesEntity::class],
    version = 1
)
abstract class FavouritesDatabase: RoomDatabase() {
    abstract val favouritesDao: FavouritesDao
}

@Entity
data class FavouritesEntity(
    @PrimaryKey val id: Long
)