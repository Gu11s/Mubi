package com.gdevs.mubi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto

@Dao
interface ShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(show: TvShowDetailDto)
}