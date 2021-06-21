package com.example.gnbmarketplace.data.server

import com.example.gnbmarketplace.domain.models.conversion.Conversion
import com.example.gnbmarketplace.domain.models.product.Product
import okhttp3.Interceptor
import okhttp3.Request
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

interface ApiService {

    companion object Factory {
        const val GET_TRANSACTIONS = "transactions.json"
        const val GET_CONVERSIONS = "rates.json"
    }

    @GET(GET_TRANSACTIONS)
    suspend fun getProducts(): Response<List<WrappedListProdResponse<Product>>>

    @GET(GET_CONVERSIONS)
    suspend fun getConversions(): Response<List<WrappedListConvResponse<Conversion>>>

}

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val requestBuilder: Request.Builder

        requestBuilder = request.newBuilder()

        return chain.proceed(requestBuilder.build())
    }
}
