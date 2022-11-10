package dev.havlicektomas.notinoproducts.data.favourites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FavouritesRepository {
    suspend fun getFavourites(): Flow<List<FavouriteItem>>
    suspend fun addFavourite(item: FavouriteItem)
    suspend fun removeFavouriteItem(item: FavouriteItem)
}

class FavouritesRepositoryImpl @Inject constructor(
    private val favouritesDatabase: FavouritesDatabase
): FavouritesRepository {

    private val dao = favouritesDatabase.favouritesDao

    override suspend fun getFavourites(): Flow<List<FavouriteItem>> {
        return dao.getFavourites().map { entities ->
            entities.favouritesFromDatabase()
        }
    }

    override suspend fun addFavourite(item: FavouriteItem) {
        dao.insertItem(item.favouriteToDatabase())
    }

    override suspend fun removeFavouriteItem(item: FavouriteItem) {
        dao.deleteItem(item.id)
    }
}