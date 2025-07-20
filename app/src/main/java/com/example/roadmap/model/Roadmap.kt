package com.example.roadmap.model

import androidx.annotation.DrawableRes

data class Roadmap(
    val name: String,
    val description: String,
    @DrawableRes val picture: Int, //TODO: use internet or local cache, not resources
    val actionPoints: List<ActionPoint>
)
