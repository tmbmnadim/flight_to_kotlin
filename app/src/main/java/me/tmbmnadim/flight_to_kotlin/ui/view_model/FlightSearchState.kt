package me.tmbmnadim.flight_to_kotlin.ui.view_model

import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite
import me.tmbmnadim.flight_to_kotlin.models.FlightRoute

data class FlightSearchState(
    val airports: List<Airport> = emptyList(),
    val destinations: List<FlightRoute> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val favoriteFlightRoutes: List<FlightRoute> = emptyList(),
//    val isLoading: Boolean = false,
    val searchText: String = "",
    val selectedAirport: Airport? = null,
    val error: String? = null,
)