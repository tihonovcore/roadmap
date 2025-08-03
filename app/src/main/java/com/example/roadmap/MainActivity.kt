package com.example.roadmap

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.roadmap.ui.theme.RoadmapTheme
import com.example.roadmap.ui.RoadmapApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tryEnableNotifications()

        enableEdgeToEdge()
        setContent {
            RoadmapTheme {
                RoadmapApp()
            }
        }
    }

    private fun tryEnableNotifications() {
        val areNotificationsEnabled = NotificationManagerCompat
            .from(applicationContext)
            .areNotificationsEnabled()
        if (areNotificationsEnabled) {
            return
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            0 //any, we don't process result in onRequestPermissionsResult
        )
    }
}
