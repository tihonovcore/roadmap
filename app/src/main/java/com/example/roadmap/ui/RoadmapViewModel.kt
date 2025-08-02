package com.example.roadmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.model.Roadmap
import com.example.roadmap.model.RoadmapState
import com.example.roadmap.network.GithubService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoadmapViewModel(
    private val githubService: GithubService
) : ViewModel() {

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

    fun addActionPointToCurrentRoadmap(actionPoint: ActionPoint) {
        _uiState.update { old ->
            val currentRoadmap = old.selectedRoadmap!!
            val roadmaps = old.roadmaps.toMutableList()
            val index = roadmaps.indexOf(currentRoadmap)
            roadmaps[index] = currentRoadmap.copy(
                actionPoints = currentRoadmap.actionPoints + actionPoint
            )

            old.copy(
                selectedRoadmap = roadmaps[index],
                roadmaps = roadmaps.toList()
            )
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RoadmapApplication
                RoadmapViewModel(application.githubService)
            }
        }
    }
}
