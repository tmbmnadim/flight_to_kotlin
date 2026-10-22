package me.tmbmnadim.flight_to_kotlin.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tmbmnadim.flight_to_kotlin.FlightSearchApplication
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite
import me.tmbmnadim.flight_to_kotlin.models.Flight
import me.tmbmnadim.flight_to_kotlin.repositories.AirportRepository
import me.tmbmnadim.flight_to_kotlin.repositories.FavoriteRepository
import kotlin.time.Duration.Companion.milliseconds


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class FlightSearchViewModel(
    val airportRepository: AirportRepository,
    val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val searchText = MutableStateFlow("")
    private val selectedAirport = MutableStateFlow<Airport?>(null)

    private val query = MutableStateFlow(Query())
    private val suggestions: Flow<List<Airport>> = query
        .map { it.text }
        .distinctUntilChanged()
        .debounce(200.milliseconds)
        .flatMapLatest { text ->
            if (text.isBlank()) flowOf(emptyList())
            else airportRepository.searchAirports(text)
        }
    private val destinations: Flow<List<Airport>> = query
        .map { it.selected }
        .distinctUntilChanged()
        .flatMapLatest { from ->
            if (from == null) flowOf(emptyList())
            else airportRepository.getDestinations(from.iataCode)
        }

    private data class SearchData(
        val query: Query,
        val suggestions: List<Airport>,
        val destinations: List<Airport>,
    )

    private data class Query(val text: String = "", val selected: Airport? = null)

    private val searchData = combine(
        query, suggestions, destinations,
        ::SearchData
    )

    val uiState: StateFlow<FlightSearchState> = combine(
        searchData,
        airportRepository.getAirports(),
        favoriteRepository.getFavorites(),
    ) { search, airports, favorites ->
        val byCode = airports.associateBy { it.iataCode }
        val favoriteFlights = favorites.mapNotNull { fav ->
            val from = byCode[fav.departureCode]
            val to = byCode[fav.destinationCode]
            if (from != null && to != null) Flight(fav.id, from, to) else null
        }
        FlightSearchState(
            searchText = search.query.text,
            airports = search.suggestions,
            possibleFlights = search.query.selected?.let { from ->
                search.destinations.map { Flight(it.id, from, it) }
            }.orEmpty(),
            favorites = favorites,
            favoriteFlights = favoriteFlights,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        FlightSearchState()
    )

    fun onAirportSelected(airport: Airport) =
        query.update { Query(text = airport.iataCode, selected = airport) }

    fun onValueChanged(value: String) =
        query.update { Query(text = value) }

    fun onFavorite(flight: Flight) = viewModelScope.launch {
        if (flight.isIn(uiState.value.favorites)) {
            favoriteRepository.removeFavorite(
                departureCode = flight.from.iataCode,
                destinationCode = flight.to.iataCode
            )
        } else {
            favoriteRepository.addFavorite(
                Favorite(
                    departureCode = flight.from.iataCode,
                    destinationCode = flight.to.iataCode
                )
            )
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val flightSearchApplication: FlightSearchApplication =
                    (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    flightSearchApplication.appContainer.airportRepository,
                    flightSearchApplication.appContainer.favoriteRepository
                )
            }
        }
    }
}


