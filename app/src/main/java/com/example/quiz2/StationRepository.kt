package com.example.quiz2

import androidx.lifecycle.LiveData

class StationRepository (private val stationDao: StationDAO) {

    var allStations: LiveData<List<StationEntity>>
    init {
        allStations = stationDao.getAll()
    }
    suspend fun insert(stationEntity: StationEntity) {
        stationDao.insert(stationEntity)
    }
}