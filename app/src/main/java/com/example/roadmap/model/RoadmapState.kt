package com.example.roadmap.model

import com.example.roadmap.data.DataProvider

data class RoadmapState(
    val selectedRoadmap: Roadmap? = null,
    val selectedActionPoint: ActionPoint? = null,
    val roadmaps: List<Roadmap> = DataProvider.roadmaps,
    val doneActionPoints: Set<ActionPoint> = setOf()
)
