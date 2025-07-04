package com.example.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auid_prefs")

@Singleton
class AuidDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val AUID_KEY = stringPreferencesKey("auid_key")
    }

    suspend fun saveAuid(value: String) {
        context.dataStore.edit { prefs ->
            prefs[AUID_KEY] = value
        }
    }

    suspend fun getAuidDatabase(): String? {
        return context.dataStore.data
            .map { it[AUID_KEY] }
            .first()
    }

    suspend fun clearAuid() {
        context.dataStore.edit { prefs ->
            prefs.remove(AUID_KEY)
        }
    }
}
