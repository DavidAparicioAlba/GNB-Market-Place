package com.example.gnbmarketplace.di

import android.content.Context
import com.example.gnbmarketplace.data.cache.SharedPreferencesManager
import com.example.gnbmarketplace.data.server.ApiService
import com.example.gnbmarketplace.domain.repository.conversions.ConversionsRepository
import com.example.gnbmarketplace.domain.repository.products.ProductsRepository
import com.example.gnbmarketplace.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.END_POINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    fun providesSharedPref(@ApplicationContext context: Context) : SharedPreferencesManager {
        return  SharedPreferencesManager(context)
    }
}

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideProductsRepository(apiService: ApiService) : ProductsRepository {
        return ProductsRepository(apiService)
    }
}

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object BankModule {

    @Provides
    @Singleton
    fun provideConversionsRepository(apiService: ApiService) : ConversionsRepository {
        return ConversionsRepository(apiService)
    }
}