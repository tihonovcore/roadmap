## Send push from backend

Generate private key at https://console.firebase.google.com/project/roadmap-c5c4d/settings/serviceaccounts/adminsdk

```kotlin
dependencies {
    implementation("com.google.firebase:firebase-admin:9.5.0")
}
```

```kotlin
val serviceAccount = FileInputStream("<path_to_private_key")

val options: FirebaseOptions = FirebaseOptions.Builder()
    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    .build()

FirebaseApp.initializeApp(options)

val message: Message = Message.builder()
    .putData("selectedActionPoint", "13")
    .putData("forTitle", "Какой-то заголовок")
    .putData("forBody", "Какое-то тело")
    .setToken("<app_fcm_token>")
    .build()


// Send a message to the devices subscribed to the provided topic.
val response = FirebaseMessaging.getInstance().send(message)
```