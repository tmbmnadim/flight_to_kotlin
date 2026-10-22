package me.tmbmnadim.flight_to_kotlin.sources

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.tmbmnadim.flight_to_kotlin.models.Airport

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("""
        SELECT * FROM airport
        WHERE name LIKE '%' || :query || '%'
        OR iata_code LIKE '%' || :query || '%'
    """)
    fun searchAirports(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE NOT iata_code = :iataCode")
    fun getDestinations(iataCode: String): Flow<List<Airport>>
}