package com.example.gnbmarketplace.domain.models.product

import com.google.gson.annotations.SerializedName

data class Product(@SerializedName("sku") var sku: String? = null,
                @SerializedName("amount") var amount: String? = null,
                @SerializedName("currency") var currency: String? = null,)