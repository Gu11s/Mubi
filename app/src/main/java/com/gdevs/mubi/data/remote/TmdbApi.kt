package com.gdevs.mubi.data.remote

import com.gdevs.mubi.data.remote.dto.TvShowDetailDto
import com.gdevs.mubi.data.remote.dto.TvShowDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("popular")
    suspend fun getTvPopular(
        @Query("api_key") apiKey: String = "678b25c918307ddb1fa96670d0f44707",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): TvShowDto

    @GET("{tv_id}")
    suspend fun getTvShowDetail(
        @Path("tv_id") tvId: Int?,
        @Query("api_key") apiKey: String = "678b25c918307ddb1fa96670d0f44707",
        @Query("language") language: String = "en-US"
    ): TvShowDetailDto


}