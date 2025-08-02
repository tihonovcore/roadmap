package com.example.roadmap

import android.app.Application
import com.example.roadmap.network.GithubService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class RoadmapApplication : Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    lateinit var githubService: GithubService

    override fun onCreate() {
        super.onCreate()
        githubService = retrofit.create(GithubService::class.java)
    }
}