package com.example.gnbmarketplace.presentation

import com.example.gnbmarketplace.data.server.WrappedListProdResponse
import com.example.gnbmarketplace.domain.models.product.Product
import com.example.gnbmarketplace.domain.models.product.ProductEntity

sealed class MainActivityScreenState
{
    object Init : MainActivityScreenState()
    data class IsLoading(val isLoading: Boolean) : MainActivityScreenState()
    data class ShowToast(val message: String) : MainActivityScreenState()
    data class ShowProducts(val productsListEntity: List<ProductEntity>) : MainActivityScreenState()
    data class ErrorShowProducts(val rawResponse: WrappedListProdResponse<Product>) : MainActivityScreenState()

}