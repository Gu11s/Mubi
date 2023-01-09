package com.gdevs.mubi.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gdevs.mubi.data.remote.dto.Result

@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ShowDataBase: RoomDatabase() {

    abstract fun showDao(): ShowDao

}