package com.example.roadmap.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey

class CustomActionPointIdsRepository(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun generateId(): Int {
        var generatedId: Int? = null

        dataStore.edit { preferences ->
            val lastId = preferences[CUSTOM_ACTION_POINT_IDS] ?: 0
            preferences[CUSTOM_ACTION_POINT_IDS] = lastId - 1

            generatedId = preferences[CUSTOM_ACTION_POINT_IDS]
        }

        return generatedId!!
    }

    private companion object {
        val CUSTOM_ACTION_POINT_IDS = intPreferencesKey("custom_action_point_ids")
    }
}