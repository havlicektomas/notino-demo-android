package dev.havlicektomas.notinoproducts.domain

data class Product(
    val productId: Long,
    val brand: String,
    val productName: String,
    val productDescription: String,
    val imageUrl: String,
    val price: Price,
    val rating: Int,
    val isFavourite: Boolean = false
)

data class Price(
    val price: Double,
    val currency: String
) {
    companion object {
        const val CURRENCY_CZK = "CZK"
    }
}
