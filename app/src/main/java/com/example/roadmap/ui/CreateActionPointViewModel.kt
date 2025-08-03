package com.example.roadmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.data.CustomActionPointIdsRepository
import com.example.roadmap.data.RoadmapDao
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.model.fromEntity
import com.example.roadmap.model.toEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreateActionPointViewModel(
    private val selectedRoadmapId: Int,
    private val roadmapDao: RoadmapDao,
    private val customActionPointIdsRepository: CustomActionPointIdsRepository,
) : ViewModel() {

    fun addActionPointToCurrentRoadmap(name: String, description: String) {
        viewModelScope.launch {
            val id = customActionPointIdsRepository.generateId()
            val actionPoint = ActionPoint(id = id, name = name, description = description)

            roadmapDao.insertActionPoint(actionPoint.toEntity(selectedRoadmapId))
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val savedStateHandle = this.createSavedStateHandle()
                val selectedRoadmapId = savedStateHandle.get<Int>("roadmapId") ?: error("No roadmapId")

                val application = this[APPLICATION_KEY] as RoadmapApplication
                CreateActionPointViewModel(
                    selectedRoadmapId = selectedRoadmapId,
                    roadmapDao = application.roadmapDatabase.createDao(),
                    customActionPointIdsRepository = application.customActionPointIdsRepository
                )
            }
        }
    }
}