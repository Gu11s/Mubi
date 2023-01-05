package com.gdevs.mubi.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TvShowDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)