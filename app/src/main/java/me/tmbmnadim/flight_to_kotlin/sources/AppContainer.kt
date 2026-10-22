package me.tmbmnadim.flight_to_kotlin.sources

import android.content.Context
import me.tmbmnadim.flight_to_kotlin.repositories.AirportRepository
import me.tmbmnadim.flight_to_kotlin.repositories.FavoriteRepository
import me.tmbmnadim.flight_to_kotlin.repositories.OfflineAirportRepository
import me.tmbmnadim.flight_to_kotlin.repositories.OfflineFavoriteRepository


interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(FlightSearchDb.getDatabase(context).airportDao())
    }
    override val favoriteRepository: FavoriteRepository by lazy {
        OfflineFavoriteRepository(FlightSearchDb.getDatabase(context).favoriteDao())
    }
}