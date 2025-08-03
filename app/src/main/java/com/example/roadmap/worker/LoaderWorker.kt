package com.example.roadmap.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.model.toEntity
import com.example.roadmap.network.GithubService

class LoaderWorker(
    ctx: Context, params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val application = applicationContext as RoadmapApplication
        val githubService: GithubService = application.githubService
        val roadmapDao = application.roadmapDatabase.createDao()

        val roadmaps = githubService.getRoadmaps()

        roadmapDao.insertRoadmaps(roadmaps.map { it.toEntity() })
        roadmapDao.insertActionPoints(
            roadmaps.flatMap { roadmap ->
                roadmap.actionPoints.map { it.toEntity(roadmap.id) }
            }
        )

        return Result.success()
    }
}