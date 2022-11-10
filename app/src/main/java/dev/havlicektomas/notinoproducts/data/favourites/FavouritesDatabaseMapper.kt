package dev.havlicektomas.notinoproducts.data.favourites

fun FavouriteItem.favouriteToDatabase(): FavouritesEntity {
    return FavouritesEntity(id = this.id)
}

fun List<FavouritesEntity>.favouritesFromDatabase(): List<FavouriteItem> {
    return this.map {
        FavouriteItem(id = it.id)
    }
}