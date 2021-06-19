package com.example.gnbmarketplace.domain.models.conversion

import com.google.gson.annotations.SerializedName

data class Conversion(@SerializedName("from") var from: Int? = null,
                @SerializedName("to") var to : String? = null,
                @SerializedName("rate") var rate: String? = null,)