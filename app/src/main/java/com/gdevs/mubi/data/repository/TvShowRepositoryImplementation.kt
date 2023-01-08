package com.gdevs.mubi.data.repository

import androidx.annotation.WorkerThread
import com.gdevs.mubi.common.Resource
import com.gdevs.mubi.data.local.ShowDao
import com.gdevs.mubi.data.remote.TmdbApi
import com.gdevs.mubi.data.remote.dto.Result
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto
import com.gdevs.mubi.data.remote.dto.TvShowDto
import com.gdevs.mubi.data.remote.dto.season.SeasonDto
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class TvShowRepositoryImplementation @Inject constructor(
    private val api: TmdbApi,
    private val showDao: ShowDao
) {

    suspend fun getTvPopular(page: Int): Resource<TvShowDto> {
        val response = try {
            api.getTvPopular(page = page)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getShowInfo(showId: Int?): Resource<TvShowDetailDto> {
        val response = try {
            api.getTvShowDetail(tvId = showId)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getSeason(showId: Int?, seasonNumber: Int?): Resource<SeasonDto> {
        val response = try {
            api.getSeason(tvId = showId, seasonNumber = seasonNumber)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(vararg show: Result) {
        showDao.insert(show = show)
    }
}