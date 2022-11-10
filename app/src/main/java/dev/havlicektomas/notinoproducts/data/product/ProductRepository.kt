package dev.havlicektomas.notinoproducts.data.product

import dev.havlicektomas.notinoproducts.domain.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

interface ProductRepository {
    suspend fun getProducts(): Flow<Result<List<Product>>>
}

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
): ProductRepository {
    override suspend fun getProducts(): Flow<Result<List<Product>>> {
        return flow {
            try {
                val productList = productApi.getProducts().vpProductByIds.productResponseToProducts()
                emit(Result.success(productList))
            } catch(e: HttpException) {
                emit(Result.failure(e))
            }
        }
    }
}