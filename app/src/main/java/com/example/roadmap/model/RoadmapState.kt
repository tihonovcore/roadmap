package com.example.roadmap.model

data class RoadmapState(
    val selectedRoadmap: Roadmap? = null,
    val selectedActionPoint: ActionPoint? = null,
    val roadmaps: List<Roadmap> = listOf(),
    val doneActionPoints: Set<ActionPoint> = setOf()
)
