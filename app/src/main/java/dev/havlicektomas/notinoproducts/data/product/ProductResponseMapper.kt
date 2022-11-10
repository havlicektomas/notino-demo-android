package dev.havlicektomas.notinoproducts.data.product

import dev.havlicektomas.notinoproducts.domain.Price
import dev.havlicektomas.notinoproducts.domain.Product

fun List<ProductDto>.productResponseToProducts(): List<Product> {
    return this.map { productDto ->
        Product(
            productId = productDto.productId,
            brand = productDto.brand.name,
            productName = productDto.name,
            productDescription = productDto.annotation,
            imageUrl = productDto.imageUrl,
            price = Price(
                price = productDto.price.value,
                currency = productDto.price.currency
            ),
            rating = productDto.reviewSummary.score
        )
    }
}