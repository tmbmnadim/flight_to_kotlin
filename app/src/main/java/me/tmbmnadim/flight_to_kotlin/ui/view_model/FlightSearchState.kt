package me.tmbmnadim.flight_to_kotlin.ui.view_model

import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite
import me.tmbmnadim.flight_to_kotlin.models.Flight

data class FlightSearchState(
    val airports: List<Airport> = emptyList(),
    val possibleFlights: List<Flight> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val favoriteFlights: List<Flight> = emptyList(),
    val isLoading: Boolean = false,
    val searchText: String = "",
    val error: String? = null,
)