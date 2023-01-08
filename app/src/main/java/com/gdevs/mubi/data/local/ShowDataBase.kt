package com.gdevs.mubi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gdevs.mubi.data.remote.dto.Result
import com.gdevs.mubi.data.remote.dto.TvShowDetailDto

@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ShowDataBase: RoomDatabase() {

    abstract fun showDao(): ShowDao

//    companion object {
//        // Singleton prevents multiple instances of database opening at the
//        // same time.
//        @Volatile
//        private var INSTANCE: ShowDataBase? = null
//
//        fun getDatabase(context: Context): ShowDataBase {
//            // if the INSTANCE is not null, then return it,
//            // if it is, then create the database
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ShowDataBase::class.java,
//                    "show_database"
//                ).build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }
//    }


}