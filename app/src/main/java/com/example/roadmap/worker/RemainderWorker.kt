package com.example.roadmap.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.roadmap.MainActivity
import com.example.roadmap.R
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.data.model.ActionPointEntity
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
            showNotification(actionPoint)
        }

        return Result.success()
    }

    private fun showNotification(actionPoint: ActionPointEntity) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(SELECTED_ACTION_POINT, actionPoint.id)
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0, //should be unique, but there is single type of intents
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, RoadmapApplication.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.work_history)
            .setContentTitle("Давно не учились")
            .setContentText("Попробуй ${actionPoint.name}")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.DEFAULT_ALL)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
    }

    companion object {
        const val SELECTED_ACTION_POINT = "selected_action_point"
    }
}
