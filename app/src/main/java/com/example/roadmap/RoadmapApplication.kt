package com.example.roadmap

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.roadmap.data.ActionPointStatusRepository
import com.example.roadmap.data.RoadmapDatabase
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

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "done_action_points"
    )

    lateinit var githubService: GithubService
    lateinit var roadmapDatabase: RoadmapDatabase
    lateinit var actionPointStatusRepository: ActionPointStatusRepository

    override fun onCreate() {
        super.onCreate()
        githubService = retrofit.create(GithubService::class.java)
        roadmapDatabase = RoadmapDatabase.getDatabase(this)
        actionPointStatusRepository = ActionPointStatusRepository(dataStore)
    }
}