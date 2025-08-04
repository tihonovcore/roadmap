package com.example.roadmap.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

fun logFcmToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("FCM", "Fetching FCM registration token failed", task.exception)
            return@addOnCompleteListener
        }

        val token = task.result
        Log.d("FCM", "Token: $token")
    }
}
