package com.example.roadmap

import android.app.Application
import com.example.roadmap.network.GithubService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RoadmapApplication : Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    lateinit var githubService: GithubService

    override fun onCreate() {
        super.onCreate()
        githubService = retrofit.create(GithubService::class.java)
    }
}