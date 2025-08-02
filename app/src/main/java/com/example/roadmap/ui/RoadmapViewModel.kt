package com.example.roadmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.data.ActionPointStatusRepository
import com.example.roadmap.data.CustomActionPointIdsRepository
import com.example.roadmap.data.RoadmapDao
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.model.Roadmap
import com.example.roadmap.model.RoadmapState
import com.example.roadmap.model.fromEntity
import com.example.roadmap.model.toEntity
import com.example.roadmap.network.GithubService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoadmapViewModel(
    private val githubService: GithubService,
    private val roadmapDao: RoadmapDao,
    private val actionPointStatusRepository: ActionPointStatusRepository,
    private val customActionPointIdsRepository: CustomActionPointIdsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoadmapState())
    val uiState = _uiState.asStateFlow()

    //TODO: для каждого экрана нужен свой view, который будет доставать нужные данные
    val roadmaps = roadmapDao.getRoadmaps()
        .combine(roadmapDao.getActionPoints()) { roadmapEntities, actionPointEntities ->
            val byRoadmap = actionPointEntities.groupBy { it.roadmapId }
            roadmapEntities.map { roadmap ->
                val actionPoints = (byRoadmap[roadmap.id] ?: emptyList())
                    .map { ap -> ap.fromEntity() }
                roadmap.fromEntity(actionPoints)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = emptyList()
        )

    val finishedActionIdsState = actionPointStatusRepository.finishedActionIds().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = emptySet()
    )

    //TODO: loading/success/fail
    init {
        viewModelScope.launch {
            val roadmaps = githubService.getRoadmaps()

            roadmapDao.insertRoadmaps(roadmaps.map { it.toEntity() })
            roadmapDao.insertActionPoints(
                roadmaps.flatMap { roadmap ->
                    roadmap.actionPoints.map { it.toEntity(roadmap.id) }
                }
            )
        }
    }

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
        viewModelScope.launch {
            actionPointStatusRepository.changeDoneStatusFor(selectedActionPoint)
        }
    }

    fun addActionPointToCurrentRoadmap(name: String, description: String) {
        viewModelScope.launch {
            val id = customActionPointIdsRepository.generateId()
            val actionPoint = ActionPoint(id = id, name = name, description = description)
            val currentRoadmap = _uiState.value.selectedRoadmap!!

            roadmapDao.insertActionPoint(actionPoint.toEntity(currentRoadmap.id))
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RoadmapApplication
                RoadmapViewModel(
                    githubService = application.githubService,
                    roadmapDao = application.roadmapDatabase.createDao(),
                    actionPointStatusRepository = application.actionPointStatusRepository,
                    customActionPointIdsRepository = application.customActionPointIdsRepository
                )
            }
        }
    }
}
