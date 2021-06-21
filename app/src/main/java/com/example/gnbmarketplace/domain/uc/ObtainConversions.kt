package com.example.gnbmarketplace.domain.uc

import com.example.gnbmarketplace.data.server.WrappedListConvResponse
import com.example.gnbmarketplace.domain.models.conversion.Conversion
import com.example.gnbmarketplace.domain.models.conversion.ConversionEntity
import com.example.gnbmarketplace.domain.repository.conversions.ConversionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtainConversions @Inject constructor(private val conversionsRepository: ConversionsRepository) {
    suspend fun execute(): Flow<BaseResult<List<ConversionEntity>, WrappedListConvResponse<Conversion>>> {
        return conversionsRepository.getConversions()
    }

}