package dev.havlicektomas.notinoproducts.data.product

import android.util.Log
import retrofit2.http.GET

interface ProductApi {

    @GET("db")
    suspend fun getProducts(): ProductsResponse

    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/cernfr1993/notino-assignment/"
        const val IMAGE_URL_PREFIX = "https://i.notino.com/detail_zoom/"
    }
}

data class ProductsResponse(
    val vpProductByIds: List<ProductDto>
)

data class ProductDto(
    val productId: Long,
    val brand: BrandDto,
    val annotation: String,
    val price: PriceDto,
    var imageUrl: String,
    val name: String,
    val reviewSummary: ReviewSummaryDto
)

data class BrandDto(
    val name: String
)

data class PriceDto(
    val value: Double,
    val currency: String
)

data class ReviewSummaryDto(
    val score: Int
)