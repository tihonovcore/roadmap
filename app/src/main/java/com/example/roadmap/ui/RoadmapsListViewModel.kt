package com.example.roadmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.data.RoadmapDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class RoadmapsListViewModel(
    private val roadmapDao: RoadmapDao,
) : ViewModel() {

    val roadmaps = roadmapDao.getRoadmaps()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = emptyList()
        )

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RoadmapApplication
                RoadmapsListViewModel(
                    roadmapDao = application.roadmapDatabase.createDao(),
                )
            }
        }
    }
}
