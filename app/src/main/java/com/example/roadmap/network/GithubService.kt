package com.example.roadmap.network

import com.example.roadmap.model.Roadmap
import retrofit2.http.GET

interface GithubService {

    @GET("/tihonovcore/roadmap/refs/heads/master/api/roadmaps.json")
    suspend fun getRoadmaps(): List<Roadmap>
}