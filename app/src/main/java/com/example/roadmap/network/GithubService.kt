package com.example.roadmap.network

import retrofit2.http.GET

interface GithubService {

    @GET("tihonovcore/roadmap/refs/heads/master/gradle.properties")
    suspend fun getGradleProps(): String
}