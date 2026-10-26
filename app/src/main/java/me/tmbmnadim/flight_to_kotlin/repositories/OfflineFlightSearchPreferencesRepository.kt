package me.tmbmnadim.flight_to_kotlin.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class OfflineFlightSearchPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : FlightSearchPreferencesRepository {
    companion object {
        val SEARCH_TEXT = stringPreferencesKey("search_text")
        const val TAG = "FlightSearchPreferencesRepo"
    }

    override val searchText: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { prefs ->
        prefs[SEARCH_TEXT] ?: ""
    }

    override suspend fun saveSearchPreferences(searchText: String) {
        dataStore.edit { prefs ->
            prefs[SEARCH_TEXT] = searchText
        }
    }
}