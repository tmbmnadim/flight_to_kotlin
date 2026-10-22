package me.tmbmnadim.flight_to_kotlin.repositories

import kotlinx.coroutines.flow.Flow
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite
import me.tmbmnadim.flight_to_kotlin.sources.AirportDao

class OfflineAirportRepository(
    private val airportDao: AirportDao
) :
    AirportRepository {
    override fun getAirports(): Flow<List<Airport>> = airportDao.getAllAirports()
    override fun searchAirports(query: String): Flow<List<Airport>> = airportDao.searchAirports(query)
    override fun getDestinations(iataCode: String): Flow<List<Airport>> = airportDao.getDestinations(iataCode)
}