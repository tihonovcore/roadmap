package com.example.roadmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.roadmap.ui.theme.RoadmapTheme
import com.example.roadmap.ui.RoadmapApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoadmapTheme {
                RoadmapApp()
            }
        }
    }
}
