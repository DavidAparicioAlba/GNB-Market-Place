package com.example.gnbmarketplace.data.server

import com.example.gnbmarketplace.domain.models.product.ProductEntity
import com.google.gson.annotations.SerializedName

data class WrappedListProdResponse<T> (
    var code: Int,
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("errors") var errors : List<String>? = null,
    @SerializedName("sku") var sku: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("currency") var currency: String? = null,
)

data class WrappedListConvResponse<T> (
        var code: Int,
        @SerializedName("message") var message : String,
        @SerializedName("status") var status : Boolean,
        @SerializedName("errors") var errors : List<String>? = null,
        @SerializedName("from") var from: String? = null,
        @SerializedName("to") var to : String? = null,
        @SerializedName("rate") var rate: String? = null,
)