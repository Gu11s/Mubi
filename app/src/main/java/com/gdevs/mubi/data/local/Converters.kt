package com.gdevs.mubi.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gdevs.mubi.data.remote.dto.Season
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    private val gson = Gson()

    @TypeConverter
    fun tvShowListToString(tvShows: List<TvShowDetailDto>): String? {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToTvShowList(json: String): List<TvShowDetailDto>? {
        if (json.isEmpty()) return emptyList()
        val listType = object : TypeToken<List<TvShowDetailDto>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun seasonListToString(seasons: List<Season>): String? {
        return gson.toJson(seasons)
    }

    @TypeConverter
    fun stringToSeason(json: String): List<Season>?{
        if (json.isEmpty()) return emptyList()
        val listType = object : TypeToken<List<Season>>() {}.type
        return gson.fromJson(json, listType)
    }

//    @TypeConverter
//    fun favsTvShowListToString(tvShows: List<FavoriteTvShow>): String? {
//        return gson.toJson(tvShows)
//    }
//
//    @TypeConverter
//    fun stringToFavsTvShowList(json: String): List<FavoriteTvShow>? {
//        if (json.isEmpty()) return emptyList()
//        val listType = object : TypeToken<List<FavoriteTvShow>>() {}.type
//        return gson.fromJson(json, listType)
//    }

    @TypeConverter
    fun stringListToString(tvShows: List<String>): String? {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToStringList(json: String): List<String>? {
        if (json.isEmpty()) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun intListToString(tvShows: List<Int>): String? {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToIntList(json: String): List<Int>? {
        if (json.isEmpty()) return emptyList()
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun tvShowToString(tvShow: TvShowDetailDto): String? {
        return gson.toJson(tvShow)
    }

    @TypeConverter
    fun stringToTvShow(json: String): TvShowDetailDto? {
        return gson.fromJson(json, TvShowDetailDto::class.java)
    }

//    @TypeConverter
//    fun favTvShowToString(tvShow: FavoriteTvShow): String? {
//        return gson.toJson(tvShow)
//    }
//
//    @TypeConverter
//    fun stringToFavTvShow(json: String): FavoriteTvShow? {
//        return gson.fromJson(json, FavoriteTvShow::class.java)
//    }


}