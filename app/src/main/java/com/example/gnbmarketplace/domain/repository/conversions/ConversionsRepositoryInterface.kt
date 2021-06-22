package com.example.gnbmarketplace.domain.repository.conversions

import com.example.gnbmarketplace.data.server.WrappedListConvResponse
import com.example.gnbmarketplace.domain.models.conversion.Conversion
import com.example.gnbmarketplace.domain.models.conversion.ConversionEntity
import com.example.gnbmarketplace.domain.uc.BaseResult
import kotlinx.coroutines.flow.Flow

interface ConversionsRepositoryInterface {

    suspend fun getConversions() : Flow<BaseResult<List<ConversionEntity>, WrappedListConvResponse<Conversion>>>

}