package com.example.roadmap.notification

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.roadmap.MainActivity
import com.example.roadmap.R
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.worker.RemainderWorker.Companion.SELECTED_ACTION_POINT
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.flow.first

class RemoteNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val selectedActionPoint = remoteMessage.data["selectedActionPoint"]?.toInt() ?: 0
        val title = remoteMessage.data["forTitle"]
        val body = remoteMessage.data["forBody"]

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(SELECTED_ACTION_POINT, selectedActionPoint)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(applicationContext, RoadmapApplication.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.work_history)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.DEFAULT_ALL)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
