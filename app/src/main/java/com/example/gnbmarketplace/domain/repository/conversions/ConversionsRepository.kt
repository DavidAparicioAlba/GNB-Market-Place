package com.example.gnbmarketplace.domain.repository.conversions

import com.example.gnbmarketplace.data.server.ApiService
import com.example.gnbmarketplace.data.server.WrappedListConvResponse
import com.example.gnbmarketplace.domain.models.conversion.Conversion
import com.example.gnbmarketplace.domain.models.conversion.ConversionEntity
import com.example.gnbmarketplace.domain.uc.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConversionsRepository @Inject constructor(val service: ApiService) : ConversionsRepositoryInterface {

    override suspend fun getConversions(): Flow<BaseResult<MutableList<ConversionEntity>, WrappedListConvResponse<Conversion>>> {
        return flow {
            val response = service.getConversions()
            if (response.isSuccessful){
                val body = response.body()!!
                val conversions = mutableListOf<ConversionEntity>()
                var conversion: ConversionEntity
                body.forEach { conversionResponse ->
                    conversion = ConversionEntity(
                            conversionResponse.from.toString(),
                            conversionResponse.to.toString(),
                            conversionResponse.rate.toString(), )
                    conversions.add(conversion)
                }
                emit(BaseResult.Success(conversions))
            }else{
                val type = object : TypeToken<WrappedListConvResponse<Conversion>>(){}.type
                val err = Gson().fromJson<WrappedListConvResponse<Conversion>>(response.errorBody()!!.charStream(), type)!!
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }

}