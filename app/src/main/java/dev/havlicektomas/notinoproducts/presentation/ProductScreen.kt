package dev.havlicektomas.notinoproducts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.havlicektomas.notinoproducts.R
import dev.havlicektomas.notinoproducts.domain.Product
import dev.havlicektomas.notinoproducts.ui.theme.NotinoproductsTheme
import dev.havlicektomas.notinoproducts.ui.theme.Pink

@Composable
fun ProductScreen(
    viewModel: ProductScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val state = viewModel.uiState.collectAsState()
    when (state.value) {
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success -> {
            SuccessScreen(
                products = (state.value as UiState.Success).data,
                onFavouriteClick = { product -> viewModel.toggleFavourite(product) },
                paddingValues = paddingValues
            )
        }
        is UiState.Error -> {
            ErrorScreen()
        }
    }
}

@Composable
fun SuccessScreen(
    products: List<Product>,
    onFavouriteClick: (product: Product) -> Unit,
    paddingValues: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(products) {product ->
            ProductCard(
                modifier = Modifier,
                product = product,
                onFavouriteClick = onFavouriteClick
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Pink
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    NotinoproductsTheme {
        LoadingScreen()
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(id = R.string.product_screen_error))
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    NotinoproductsTheme {
        ErrorScreen()
    }
}