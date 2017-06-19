package com.erostech.kiki.modules

import com.erostech.kiki.BASE_API_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by erosgarciaponte on 19.06.17.
 */
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }
}