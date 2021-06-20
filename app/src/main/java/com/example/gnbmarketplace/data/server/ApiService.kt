package com.example.gnbmarketplace.data.server

import com.example.gnbmarketplace.domain.models.conversion.Conversion
import com.example.gnbmarketplace.domain.models.product.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    companion object Factory {
        const val GET_TRANSACTIONS = "transactions.json"
        const val GET_CONVERSIONS = "rates.json"
    }

    @GET(GET_TRANSACTIONS)
    suspend fun getProducts(): Response<WrappedListResponse<Product>>

    @GET(GET_CONVERSIONS)
    suspend fun getConversions(): Response<WrappedListResponse<Conversion>>

}
