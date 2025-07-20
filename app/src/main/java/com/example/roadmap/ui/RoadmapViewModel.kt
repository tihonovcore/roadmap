package com.example.roadmap.ui

import androidx.lifecycle.ViewModel
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.model.Roadmap
import com.example.roadmap.model.RoadmapState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoadmapViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RoadmapState())
    val uiState = _uiState.asStateFlow()

    fun chooseRoadmap(roadmap: Roadmap) {
        _uiState.update { old ->
            old.copy(selectedRoadmap = roadmap)
        }
    }

    fun chooseActionPoint(actionPoint: ActionPoint) {
        _uiState.update { old ->
            old.copy(selectedActionPoint = actionPoint)
        }
    }

    fun changeDoneStatus(selectedActionPoint: ActionPoint) {
        _uiState.update { old ->
            if (selectedActionPoint in old.doneActionPoints) {
                old.copy(doneActionPoints = old.doneActionPoints - selectedActionPoint)
            } else {
                old.copy(doneActionPoints = old.doneActionPoints + selectedActionPoint)
            }
        }
    }
}
