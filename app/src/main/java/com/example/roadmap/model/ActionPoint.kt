package com.example.roadmap.model

import kotlinx.serialization.Serializable

@Serializable
data class ActionPoint(
    val id: Int,
    val name: String,
    val description: String,
    val link: String? = null
)
