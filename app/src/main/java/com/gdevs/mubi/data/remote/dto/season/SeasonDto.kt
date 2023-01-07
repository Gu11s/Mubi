package com.gdevs.mubi.data.remote.dto.season


import com.google.gson.annotations.SerializedName

data class SeasonDto(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episodes")
    val episodes: List<Episode>,
    @SerializedName("_id")
    val privateId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int
)