package com.bagus.pemantauanbencana.ui.about

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val EMAIL= stringPreferencesKey("email")
    private val NAME = stringPreferencesKey("name")
    private val DARK_MODE = booleanPreferencesKey("dark_mode")
    private val NOTIFICATION = booleanPreferencesKey("notification")

    fun getEmail(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[EMAIL] ?: "EMAIL"
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[NAME] ?: "NAME"
        }
    }

    fun getNotification(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[NOTIFICATION] ?: false
        }
    }

    suspend fun setUser(email: String, name: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
            preferences[NAME] = name
        }
    }

    suspend fun saveNotification(notif: Boolean) {
        dataStore.edit { preference ->
            preference[NOTIFICATION] = notif
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[DARK_MODE] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}