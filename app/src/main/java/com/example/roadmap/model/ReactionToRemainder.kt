package com.example.roadmap.model

data class ReactionToRemainder(
    val selectedActionPoint: Int = Int.MAX_VALUE
) {
    fun isPresent() = selectedActionPoint != Int.MAX_VALUE
}
