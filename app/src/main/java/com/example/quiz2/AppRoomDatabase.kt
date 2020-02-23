package com.example.quiz2

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope

@Database(entities = [StationEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
   abstract fun stationDAO(): StationDAO
   companion object{
      @Volatile
      private var INSTANCE: AppRoomDatabase? = null

      fun getDatabase(
         context: Context,
         scope: CoroutineScope
      ): AppRoomDatabase {
         // if the INSTANCE is not null, then return it,
         // if it is, then create the database
         return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
               context.applicationContext,
               AppRoomDatabase::class.java,
               "word_database"
            )
               .build()

            INSTANCE = instance
            // return instance
            instance
         }
      }
   }
}