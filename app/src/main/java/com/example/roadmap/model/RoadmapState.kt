package com.example.roadmap.model

data class RoadmapState(
    val selectedRoadmap: Roadmap? = null, //TODO: use id
    val selectedActionPoint: ActionPoint? = null, //TODO: use id
    val roadmaps: List<Roadmap> = listOf(),
    val doneActionPoints: Set<ActionPoint> = setOf()
)
