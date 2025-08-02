package com.example.roadmap.model

import kotlinx.serialization.Serializable

@Serializable
data class Roadmap(
    val name: String,
    val description: String,
    val picture: String,
    val actionPoints: List<ActionPoint>
)
