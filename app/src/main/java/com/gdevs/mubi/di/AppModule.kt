package com.gdevs.mubi.di

import android.content.Context
import androidx.room.Room
import com.gdevs.mubi.common.Constants.BASE_URL
import com.gdevs.mubi.data.local.ShowDao
import com.gdevs.mubi.data.local.ShowDataBase
import com.gdevs.mubi.data.remote.TmdbApi
import com.gdevs.mubi.data.repository.TvShowRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        api: TmdbApi,
        db: ShowDataBase
    ) = TvShowRepositoryImplementation(api, db.showDao())

    @Singleton
    @Provides
    fun provideTmdbApi(): TmdbApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TmdbApi::class.java)
    }

    @Singleton
    @Provides
    fun provideShowDao(showDB: ShowDataBase): ShowDao {
        return showDB.showDao()
    }

    @Singleton
    @Provides
    fun provideShowDataBase(@ApplicationContext context: Context): ShowDataBase {
        return Room.databaseBuilder(
            context,
            ShowDataBase::class.java,
            "show_database"
        ).build()
    }

}