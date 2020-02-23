package com.example.quiz2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StationDAO {
    @Query("select * from mrt_table")
    fun getAll(): LiveData<List<StationEntity>>

    // Don't use for production env!
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stationEntity: StationEntity)
}