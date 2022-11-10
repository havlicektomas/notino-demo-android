package dev.havlicektomas.notinoproducts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.havlicektomas.notinoproducts.data.favourites.FavouriteItem
import dev.havlicektomas.notinoproducts.data.favourites.FavouritesRepository
import dev.havlicektomas.notinoproducts.data.product.ProductRepository
import dev.havlicektomas.notinoproducts.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val favouritesRepository: FavouritesRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            productRepository.getProducts().combine(favouritesRepository.getFavourites()) { result, favourites ->
                if (result.isSuccess) {
                    val products = result.getOrNull()

                    if (products == null) {
                        _uiState.value = UiState.Error(Throwable("error loading products"))
                        return@combine
                    }

                    val mappedProducts = products.map { product ->
                        val isFavouriteItem = if (favourites.isEmpty()) false else favourites.contains(FavouriteItem(product.productId))
                        product.copy(isFavourite = isFavouriteItem)
                    }
                    _uiState.value = UiState.Success(mappedProducts)
                } else {
                    _uiState.value = UiState.Error(Throwable(result.exceptionOrNull()))
                }
            }.collect()
        }
    }

    fun toggleFavourite(product: Product) {
        if (product.isFavourite) {
            removeFavourite(product.productId)
        } else {
            addFavourite(product.productId)
        }
    }

    private fun addFavourite(id: Long) {
        viewModelScope.launch {
            favouritesRepository.addFavourite(FavouriteItem(id))
        }
    }

    private fun removeFavourite(id: Long) {
        viewModelScope.launch {
            favouritesRepository.removeFavouriteItem(FavouriteItem(id))
        }
    }
}

sealed interface UiState {
    object Loading: UiState
    data class Success(val data: List<Product>): UiState
    data class Error(val error: Throwable): UiState
}