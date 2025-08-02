package com.example.roadmap.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roadmaps")
data class RoadmapEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val picture: String,
)
