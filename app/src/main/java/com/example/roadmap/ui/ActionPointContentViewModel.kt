package com.example.roadmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.data.ActionPointStatusRepository
import com.example.roadmap.data.CustomActionPointIdsRepository
import com.example.roadmap.data.RoadmapDao
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.model.fromEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActionPointContentViewModel(
    private val selectedActionPointId: Int,
    private val roadmapDao: RoadmapDao,
    private val actionPointStatusRepository: ActionPointStatusRepository,
) : ViewModel() {

    val selectedActionPoint = roadmapDao.getActionPointById(selectedActionPointId)
        .map { it.fromEntity() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = ActionPoint(id = 0, name = "", description = "")
        )

    fun changeDoneStatus(selectedActionPoint: ActionPoint) {
        viewModelScope.launch {
            actionPointStatusRepository.changeDoneStatusFor(selectedActionPoint)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val savedStateHandle = this.createSavedStateHandle()
                val selectedActionPointId = savedStateHandle.get<Int>("actionPointId") ?: error("No roadmapId")

                val application = this[APPLICATION_KEY] as RoadmapApplication
                ActionPointContentViewModel(
                    selectedActionPointId = selectedActionPointId,
                    roadmapDao = application.roadmapDatabase.createDao(),
                    actionPointStatusRepository = application.actionPointStatusRepository,
                )
            }
        }
    }
}
