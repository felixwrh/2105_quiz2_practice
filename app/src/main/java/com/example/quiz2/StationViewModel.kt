package com.example.quiz2

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StationViewModel(context : Context) : ViewModel() {
    private var repository: StationRepository

    val TAG:String = "FourDigitViewModel"
    lateinit var stationList: LiveData<List<StationEntity>>
    init {
        Log.i(TAG, "FourDigitViewModel created!")
        val fourDigitDao = AppRoomDatabase.getDatabase(context, viewModelScope).stationDAO()
        repository = StationRepository(fourDigitDao)
        stationList = repository.allStations
    }

    fun add(stationEntity: StationEntity) = viewModelScope.launch {
        // We need to add the data into Room
        repository.insert(stationEntity)
    }
}



class StationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StationViewModel::class.java)) {
            return StationViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}