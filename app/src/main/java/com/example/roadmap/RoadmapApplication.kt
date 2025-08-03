package com.example.roadmap

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.roadmap.data.ActionPointStatusRepository
import com.example.roadmap.data.CustomActionPointIdsRepository
import com.example.roadmap.data.RoadmapDatabase
import com.example.roadmap.network.GithubService
import com.example.roadmap.worker.LoaderWorker
import com.example.roadmap.worker.RemainderWorker
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.time.Duration

class RoadmapApplication : Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "action_points_info"
    )

    lateinit var githubService: GithubService
    lateinit var roadmapDatabase: RoadmapDatabase
    lateinit var actionPointStatusRepository: ActionPointStatusRepository
    lateinit var customActionPointIdsRepository: CustomActionPointIdsRepository

    override fun onCreate() {
        super.onCreate()
        githubService = retrofit.create(GithubService::class.java)
        roadmapDatabase = RoadmapDatabase.getDatabase(context = this)
        actionPointStatusRepository = ActionPointStatusRepository(dataStore)
        customActionPointIdsRepository = CustomActionPointIdsRepository(dataStore)

        scheduleRoadmapsLoading()
        scheduleRemainder()
    }

    private fun scheduleRoadmapsLoading() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val loadRoadmaps = OneTimeWorkRequestBuilder<LoaderWorker>()
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context = this).enqueue(loadRoadmaps)
    }

    private fun scheduleRemainder() {
        createNotificationChannel()

        val loadRoadmaps = OneTimeWorkRequestBuilder<RemainderWorker>()
            //NOTE: this delay is used for demonstration purpose
            .setInitialDelay(Duration.ofSeconds(15))
            .build()
        WorkManager.getInstance(context = this).enqueue(loadRoadmaps)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.study_remainder),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.study_remainder_channel)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID: String = "reminder"
    }
}