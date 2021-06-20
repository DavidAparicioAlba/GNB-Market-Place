package com.example.gnbmarketplace.domain.repository.products

import com.example.gnbmarketplace.data.server.WrappedListResponse
import com.example.gnbmarketplace.domain.models.product.Product
import com.example.gnbmarketplace.domain.models.product.ProductEntity
import com.example.gnbmarketplace.domain.uc.BaseResult
import kotlinx.coroutines.flow.Flow

interface ProductsRepositoryInterface {

    suspend fun getProducts(): Flow<BaseResult<List<ProductEntity>, WrappedListResponse<Product>>>

}