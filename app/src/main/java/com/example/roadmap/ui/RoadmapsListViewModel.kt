package com.example.roadmap.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roadmap.RoadmapApplication
import com.example.roadmap.data.RoadmapDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RoadmapsListViewModel(
    private val roadmapDao: RoadmapDao,
    private val sensorManager: SensorManager,
) : ViewModel(), SensorEventListener {

    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var isDown = false
    private var lastDown = 0L

    private val _blurText = MutableStateFlow(false)
    val blurText: StateFlow<Boolean> = _blurText

    init {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) {
            return
        }

        val z = event.values[2]

        if (z < -8 && !isDown) {
            lastDown = event.timestamp
            isDown = true

            return
        }

        if (z > 8) {
            isDown = false
        }

        val gapMills = (event.timestamp - lastDown) / 1_000_000
        if (z > 8 && gapMills < 500) {
            _blurText.value = !_blurText.value
            lastDown = 0
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    val roadmaps = roadmapDao.getRoadmaps()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = emptyList()
        )

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as RoadmapApplication
                RoadmapsListViewModel(
                    roadmapDao = application.roadmapDatabase.createDao(),
                    sensorManager = application.applicationContext.getSystemService(SensorManager::class.java)
                )
            }
        }
    }
}
