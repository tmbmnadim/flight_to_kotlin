package me.tmbmnadim.flight_to_kotlin.sources

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import me.tmbmnadim.flight_to_kotlin.repositories.AirportRepository
import me.tmbmnadim.flight_to_kotlin.repositories.FavoriteRepository
import me.tmbmnadim.flight_to_kotlin.repositories.FlightSearchPreferencesRepository
import me.tmbmnadim.flight_to_kotlin.repositories.OfflineAirportRepository
import me.tmbmnadim.flight_to_kotlin.repositories.OfflineFavoriteRepository
import me.tmbmnadim.flight_to_kotlin.repositories.OfflineFlightSearchPreferencesRepository

interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository

    val flightSearchPrefsRepo: FlightSearchPreferencesRepository
}


private const val FLIGHT_PREFS_NAME = "flight_search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(FLIGHT_PREFS_NAME)

class AppDataContainer(private val context: Context): AppContainer {

    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(FlightSearchDb.getDatabase(context).airportDao())
    }
    override val favoriteRepository: FavoriteRepository by lazy {
        OfflineFavoriteRepository(FlightSearchDb.getDatabase(context).favoriteDao())
    }

    override val flightSearchPrefsRepo: FlightSearchPreferencesRepository by lazy {
        OfflineFlightSearchPreferencesRepository(context.dataStore)
    }
}