package com.example.roadmap.model

data class Roadmap(
    val name: String,
    val description: String,
    val picture: String,
    val actionPoints: List<ActionPoint>
)
