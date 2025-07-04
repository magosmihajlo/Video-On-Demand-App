package com.example.data.di

import android.content.Context
import com.example.data.local.AuidDataStore
import com.example.data.remote.VodApi
import com.example.data.remote.RetrofitInstance
import com.example.data.repository.AdvertisingIdRepositoryImpl
import com.example.data.repository.AuidRepositoryImpl
import com.example.data.repository.SeriesMetadataRepositoryImpl
import com.example.data.repository.SeriesSeasonsRepositoryImpl
import com.example.data.repository.SingleWorkRepositoryImpl
import com.example.data.repository.VodListRepositoryImpl
import com.example.domain.repository.AdvertisingIdRepository
import com.example.domain.repository.AuidRepository
import com.example.domain.repository.SeriesMetadataRepository
import com.example.domain.repository.SeriesSeasonsRepository
import com.example.domain.repository.SingleWorkRepository
import com.example.domain.repository.VodListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideVodApi(
        @Named("VOD_API_KEY") apiKey: String
    ): VodApi {
        RetrofitInstance.setApiKey(apiKey)
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideSeriesSeasonsRepository(
        vodApi: VodApi
    ): SeriesSeasonsRepository = SeriesSeasonsRepositoryImpl(vodApi)


    @Provides
    @Singleton
    fun provideAuidDataStore(@ApplicationContext context: Context) : AuidDataStore {
        return AuidDataStore(context)
    }

    @Provides
    @Singleton
    fun provideAuidRepository(auidDataStore: AuidDataStore): AuidRepository {
        return AuidRepositoryImpl(auidDataStore)
    }

    @Provides
    @Singleton
    fun provideVodListRepository(
        auidRepository: AuidRepository,
        advertisingIdRepository: AdvertisingIdRepository
    ): VodListRepository {
        return VodListRepositoryImpl(
            auidRepository,
            advertisingIdRepository
        )
    }

    @Provides
    @Singleton
    fun provideAdvertisingIdRepository(
        @ApplicationContext context: Context
    ): AdvertisingIdRepository = AdvertisingIdRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesSingleWorkRepository(
        vodApi: VodApi
    ): SingleWorkRepository = SingleWorkRepositoryImpl(vodApi)

    @Provides
    @Singleton
    fun provideSeriesMetadataRepository(
        vodApi: VodApi
    ): SeriesMetadataRepository = SeriesMetadataRepositoryImpl(vodApi)

}
