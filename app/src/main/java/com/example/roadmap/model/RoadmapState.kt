package com.example.roadmap.model

data class RoadmapState(
    val selectedRoadmap: Roadmap? = null,
    val selectedActionPoint: ActionPoint? = null,
    val doneActionPoints: Set<ActionPoint> = setOf()
)
