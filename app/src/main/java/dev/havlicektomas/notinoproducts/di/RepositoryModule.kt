package dev.havlicektomas.notinoproducts.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.havlicektomas.notinoproducts.data.favourites.FavouritesRepository
import dev.havlicektomas.notinoproducts.data.favourites.FavouritesRepositoryImpl
import dev.havlicektomas.notinoproducts.data.product.ProductRepository
import dev.havlicektomas.notinoproducts.data.product.ProductRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        repositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindFavouritesRepository(
        repositoryImpl: FavouritesRepositoryImpl
    ): FavouritesRepository
}