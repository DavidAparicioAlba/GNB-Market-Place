package com.example.gnbmarketplace.domain.uc

import com.example.gnbmarketplace.data.server.WrappedListResponse
import com.example.gnbmarketplace.domain.models.product.Product
import com.example.gnbmarketplace.domain.models.product.ProductEntity
import com.example.gnbmarketplace.domain.repository.products.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtainProducts @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend fun execute(): Flow<BaseResult<List<ProductEntity>, WrappedListResponse<Product>>> {
        return productsRepository.getProducts()
    }

}