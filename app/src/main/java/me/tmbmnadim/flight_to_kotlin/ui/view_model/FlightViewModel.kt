package me.tmbmnadim.flight_to_kotlin.ui.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite
import me.tmbmnadim.flight_to_kotlin.models.Flight

data class FlightState(
    val airports: List<Airport> = emptyList(),
    val possibleFlights: List<Flight> = emptyList(),
    val favorites: List<Flight> = emptyList(),
    val isLoading: Boolean = false,
    val searchText: String = "",
    val error: String? = null,
)

class FlightViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        FlightState(
            airports = listOf(
                Airport(id = 0, iataCode = "AAA", name = "Airport A", passengers = 100),
                Airport(id = 1, iataCode = "BBB", name = "Airport B", passengers = 100),
                Airport(id = 2, iataCode = "CCC", name = "Airport C", passengers = 100),
                Airport(id = 3, iataCode = "DDD", name = "Airport D", passengers = 100),
            ),
            favorites = listOf(
                Flight(
                    id = 0,
                    from = Airport(id = 0, iataCode = "AAA", name = "Airport A", passengers = 100),
                    to = Airport(id = 1, iataCode = "BBB", name = "Airport B", passengers = 100),
                ),
                Flight(
                    id = 1,
                    from = Airport(id = 1, iataCode = "BBB", name = "Airport B", passengers = 100),
                    to = Airport(id = 0, iataCode = "AAA", name = "Airport A", passengers = 100),
                )
            )
        )
    )
    val uiState: StateFlow<FlightState> = _uiState.asStateFlow()

    fun search(airport: Airport) {
        val otherAirports: List<Airport> = uiState.value.airports.filter { it.id != airport.id }
        val possibleFlights: List<Flight> = otherAirports.map {
            // Using index of airport as id for no particular reason
            Flight(
                id = otherAirports.indexOf(it),
                from = airport,
                to = it,
            )
        }
        _uiState.update {
            uiState.value.copy(searchText = airport.iataCode, possibleFlights = possibleFlights)
        }
    }

    fun onValueChanged(value: String) {
        _uiState.update {
            uiState.value.copy(searchText = value, possibleFlights = listOf())
        }
    }

    fun onFavorite(flight: Flight) {
        _uiState.update {
            uiState.value.copy(
                favorites = if (uiState.value.favorites.contains(flight))
                    uiState.value.favorites - flight
                else uiState.value.favorites + flight,
            )
        }
    }
}