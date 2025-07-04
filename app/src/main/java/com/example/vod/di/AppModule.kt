package com.example.vod.di

import com.example.vod.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("VOD_API_KEY")
    fun provideVodApiKey(): String = BuildConfig.VOD_API_KEY
}
