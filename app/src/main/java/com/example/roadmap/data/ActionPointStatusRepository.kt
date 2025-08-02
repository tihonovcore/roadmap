package com.example.roadmap.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.roadmap.model.ActionPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActionPointStatusRepository(
    private val dataStore: DataStore<Preferences>
) {
    fun finishedActionIds(): Flow<Set<Int>> {
        return dataStore.data
            .map { preferences -> preferences[FINISHED_ACTION_POINTS] ?: emptySet() }
            .map { set -> set.map { it.toInt() }.toSet() }
    }

    suspend fun changeDoneStatusFor(actionPoint: ActionPoint) {
        dataStore.edit { preferences ->
            val id = actionPoint.id.toString()
            val old = preferences[FINISHED_ACTION_POINTS] ?: emptySet()
            preferences[FINISHED_ACTION_POINTS] = if (old.contains(id)) old - id else old + id
        }
    }

    private companion object {
        val FINISHED_ACTION_POINTS = stringSetPreferencesKey("finished_action_points")
    }
}