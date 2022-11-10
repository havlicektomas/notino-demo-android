package dev.havlicektomas.notinoproducts.data.product

import dev.havlicektomas.notinoproducts.domain.Price
import dev.havlicektomas.notinoproducts.domain.Product
import org.junit.Test
import org.junit.Assert.assertEquals

class ProductResponseMapperTest {

    @Test
    fun `it should map product dto to product`() {
        val productDto = ProductDto(
            productId = 123456,
            brand = BrandDto(name = "Test brand"),
            annotation = "Test product description",
            price = PriceDto(
                value = 123.00,
                currency = "CZK"
            ),
            imageUrl = "image.png",
            name = "Test product",
            reviewSummary = ReviewSummaryDto(score = 5)
        )

        val actual = listOf(productDto).productResponseToProducts().first()

        val expected = Product(
            productId = 123456,
            brand = "Test brand",
            productName = "Test product",
            productDescription = "Test product description",
            imageUrl = "image.png",
            price = Price(
                price = 123.00,
                currency = "CZK"
            ),
            rating = 5
        )

        assertEquals(expected, actual)
    }
}