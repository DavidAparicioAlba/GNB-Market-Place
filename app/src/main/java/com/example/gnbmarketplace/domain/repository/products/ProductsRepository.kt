package com.example.gnbmarketplace.domain.repository.products

import com.example.gnbmarketplace.data.server.ApiService
import com.example.gnbmarketplace.data.server.WrappedListProdResponse
import com.example.gnbmarketplace.domain.models.product.Product
import com.example.gnbmarketplace.domain.models.product.ProductEntity
import com.example.gnbmarketplace.domain.uc.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(val service: ApiService) : ProductsRepositoryInterface {

    override suspend fun getProducts(): Flow<BaseResult<List<ProductEntity>, WrappedListProdResponse<Product>>> {

        return flow {
            val response = service.getProducts()
            if (response.isSuccessful){
                val body = response.body()!!
                val products = mutableListOf<ProductEntity>()
                var product: ProductEntity
                body.forEach { productResponse ->
                    product = ProductEntity(
                            productResponse.sku.toString(),
                            productResponse.amount.toString(),
                            productResponse.currency.toString(), )
                    products.add(product)
                }
                emit(BaseResult.Success(products))
            }else{
                val type = object : TypeToken<WrappedListProdResponse<Product>>(){}.type
                val err = Gson().fromJson<WrappedListProdResponse<Product>>(response.errorBody()!!.charStream(), type)!!
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }

}