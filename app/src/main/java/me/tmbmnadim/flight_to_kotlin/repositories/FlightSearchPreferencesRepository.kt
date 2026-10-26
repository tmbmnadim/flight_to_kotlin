package me.tmbmnadim.flight_to_kotlin.repositories

import kotlinx.coroutines.flow.Flow

interface FlightSearchPreferencesRepository {
    val searchText: Flow<String>

    suspend fun saveSearchPreferences(searchText: String)
}