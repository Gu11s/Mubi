package com.gdevs.mubi.di

import com.gdevs.mubi.common.Constants.BASE_URL
import com.gdevs.mubi.data.remote.TmdbApi
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideTvShowRepository(
        api: TmdbApi
    ) = TvShowRepositoryImplementation(api)

    @Singleton
    @Provides
    fun provideTmdbApi(): TmdbApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TmdbApi::class.java)
    }

}