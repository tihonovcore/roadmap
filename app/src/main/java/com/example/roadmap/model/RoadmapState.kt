package com.example.roadmap.model

data class RoadmapState(
    val selectedActionPoint: ActionPoint? = null, //TODO: use id
    val roadmaps: List<Roadmap> = listOf(),
)
