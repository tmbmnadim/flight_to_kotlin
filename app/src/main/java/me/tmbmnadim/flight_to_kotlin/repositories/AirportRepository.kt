package me.tmbmnadim.flight_to_kotlin.repositories

import kotlinx.coroutines.flow.Flow
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite

interface AirportRepository {
    fun getAirports(): Flow<List<Airport>>
    fun searchAirports(query: String): Flow<List<Airport>>
    fun getDestinations(iataCode: String): Flow<List<Airport>>
}