package com.example.roadmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.data.RoadmapDao
import com.example.roadmap.model.fromEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ActionPointsListViewModel(
    private val selectedRoadmapId: Int,
    private val roadmapDao: RoadmapDao
) : ViewModel() {

    val actionPoints = roadmapDao.getActionPointsByRoadmapId(selectedRoadmapId)
        .map { list -> list.map { it.fromEntity() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = listOf()
        )

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val savedStateHandle = this.createSavedStateHandle()
                val selectedRoadmapId = savedStateHandle.get<Int>("roadmapId") ?: error("No roadmapId")

                val application = this[APPLICATION_KEY] as RoadmapApplication
                ActionPointsListViewModel(
                    selectedRoadmapId = selectedRoadmapId,
                    roadmapDao = application.roadmapDatabase.createDao()
                )
            }
        }
    }
}