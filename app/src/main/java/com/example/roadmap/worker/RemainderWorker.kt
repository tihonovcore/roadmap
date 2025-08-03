package com.example.roadmap.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.roadmap.R
import com.example.roadmap.RoadmapApplication
import kotlinx.coroutines.flow.first

class RemainderWorker(
    ctx: Context, params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val application = applicationContext as RoadmapApplication

        val roadmapDao = application.roadmapDatabase.createDao()
        val statusRepository = application.actionPointStatusRepository

        val actionPoints = roadmapDao.getActionPoints().first()
        val finishedActionPoints = statusRepository.finishedActionIds().first()

        val undoneActionPoints = actionPoints
            .filterNot { it.id in finishedActionPoints }
            .toSet()

        if (undoneActionPoints.isNotEmpty()) {
            val actionPoint = undoneActionPoints.shuffled().first()
            showNotification("Попробуй ${actionPoint.name}")
        }

        return Result.success()
    }

    private fun showNotification(text: String) {
        val notification = NotificationCompat.Builder(applicationContext, RoadmapApplication.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.work_history)
            .setContentTitle("Давно не учились")
            .setContentText(text)
            .setPriority(NotificationCompat.DEFAULT_ALL)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
    }
}
