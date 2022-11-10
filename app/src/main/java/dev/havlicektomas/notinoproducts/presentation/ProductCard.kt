package dev.havlicektomas.notinoproducts.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.havlicektomas.notinoproducts.R
import dev.havlicektomas.notinoproducts.data.product.ProductApi
import dev.havlicektomas.notinoproducts.domain.Price
import dev.havlicektomas.notinoproducts.domain.Product
import dev.havlicektomas.notinoproducts.ui.theme.*

@Composable
fun ProductCard(
    modifier: Modifier,
    product: Product,
    onFavouriteClick: (product: Product) -> Unit
) {
    var basketButtonState by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.width(160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onFavouriteClick(product) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (product.isFavourite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = stringResource(id = R.string.product_card_favourite_icon),
                    modifier = Modifier.size(16.dp),
                    tint = if (product.isFavourite) {
                        Pink
                    } else {
                        InkPrimary
                    }
                )
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data("${ProductApi.IMAGE_URL_PREFIX}${product.imageUrl}")
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.product_card_product_image),
            modifier = Modifier.size(160.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = product.brand,
            fontSize = 14.sp,
            color = InkTertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.productName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = InkPrimary,
            textAlign = TextAlign.Center
        )
        Text(
            text = product.productDescription,
            fontSize = 14.sp,
            color = InkPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        RatingBar(rating = product.rating)
        Spacer(modifier = Modifier.height(12.dp))
        PriceLabel(price = product.price)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            basketButtonState = !basketButtonState
        }) {
            Text(text = if (basketButtonState) {
                stringResource(id = R.string.product_card_in_basket_button_label)
            } else {
                stringResource(id = R.string.product_card_to_basket_button_label)
            })
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun RatingBar(rating: Int) {
    Row(modifier = Modifier) {
        RatingIcon(tint = if (rating == 0) Grey else InkPrimary)
        RatingIcon(tint = if (rating > 1) InkPrimary else Grey)
        RatingIcon(tint = if (rating > 2) InkPrimary else Grey)
        RatingIcon(tint = if (rating > 3) InkPrimary else Grey)
        RatingIcon(tint = if (rating > 4) InkPrimary else Grey)
    }
}

@Composable
fun RatingIcon(tint: Color) {
    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = stringResource(id = R.string.product_card_rating_icon),
        modifier = Modifier.size(16.dp),
        tint = tint
    )
}

@Composable
fun PriceLabel(price: Price) {
    val prefix = stringResource(id = R.string.product_card_price_label_prefix)
    val currency = stringResource(id = R.string.product_card_price_label_czk)
    Text(text = "$prefix ${price.price} $currency")
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    NotinoproductsTheme {
        ProductCard(
            modifier = Modifier,
            product = Product(
                productId = 123456,
                brand = "Test brand",
                productName = "Test product",
                productDescription = "test product description goes here",
                imageUrl = "https://i.notino.com/detail_zoom/image.png",
                price = Price(
                    price = 123.00,
                    currency = "CZK"
                ),
                rating = 4
            ),
            onFavouriteClick = {}
        )
    }
}