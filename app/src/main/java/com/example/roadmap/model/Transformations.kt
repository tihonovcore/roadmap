package com.example.roadmap.model

import com.example.roadmap.data.model.ActionPointEntity
import com.example.roadmap.data.model.RoadmapEntity

fun ActionPoint.toEntity(roadmapId: Int): ActionPointEntity {
    return ActionPointEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        roadmapId = roadmapId
    )
}

fun Roadmap.toEntity(): RoadmapEntity {
    return RoadmapEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        picture = this.picture
    )
}
