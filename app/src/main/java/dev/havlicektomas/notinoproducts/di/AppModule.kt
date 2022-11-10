package dev.havlicektomas.notinoproducts.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.havlicektomas.notinoproducts.data.favourites.FavouritesDatabase
import dev.havlicektomas.notinoproducts.data.favourites.FavouritesRepository
import dev.havlicektomas.notinoproducts.data.favourites.FavouritesRepositoryImpl
import dev.havlicektomas.notinoproducts.data.product.ProductApi
import dev.havlicektomas.notinoproducts.data.product.ProductRepository
import dev.havlicektomas.notinoproducts.data.product.ProductRepositoryImpl
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductApi(): ProductApi {
        return Retrofit.Builder()
            .baseUrl(ProductApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFavouritesDatabase(app: Application): FavouritesDatabase {
        return Room.databaseBuilder(
            app,
            FavouritesDatabase::class.java,
            "favourites.db"
        ).build()
    }
}