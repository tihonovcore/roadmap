package com.example.roadmap.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "action_points",
    foreignKeys = [
        ForeignKey(
            entity = RoadmapEntity::class,
            parentColumns = ["id"],
            childColumns = ["roadmap_id"]
        )
    ]
)
data class ActionPointEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val description: String,
    val link: String? = null,
    @ColumnInfo(name = "roadmap_id")
    val roadmapId: Int,
)
