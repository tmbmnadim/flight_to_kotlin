package me.tmbmnadim.flight_to_kotlin.ui.view_model

import android.util.Log
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
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tmbmnadim.flight_to_kotlin.FlightSearchApplication
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite
import me.tmbmnadim.flight_to_kotlin.models.FlightRoute
import me.tmbmnadim.flight_to_kotlin.repositories.AirportRepository
import me.tmbmnadim.flight_to_kotlin.repositories.FavoriteRepository
import me.tmbmnadim.flight_to_kotlin.repositories.FlightSearchPreferencesRepository
import okio.IOException
import kotlin.collections.map
import kotlin.time.Duration.Companion.milliseconds


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class FlightSearchViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository,
    private val flightSearchPrefsRepo: FlightSearchPreferencesRepository
) : ViewModel() {
    private data class SearchData(
        val query: Query,
        val suggestions: List<Airport>,
        val destinations: List<FlightRoute>,
    )

    private data class Query(val text: String = "", val selected: Airport? = null)

    private val query = MutableStateFlow(Query())
    private val suggestions: Flow<List<Airport>> = query
        .map { it.text }
        .distinctUntilChanged()
        .debounce(200.milliseconds)
        .flatMapLatest { text ->
            if (text.isBlank()) flowOf(emptyList())
            else airportRepository.searchAirports(text)
        }
    private val destinations: Flow<List<FlightRoute>> = query
        .map { it.selected }
        .distinctUntilChanged()
        .flatMapLatest { from ->
            if (from == null) flowOf(emptyList())
            else airportRepository.getDestinations(from.iataCode)
                .map { list -> list.map{ FlightRoute(it.id, from, it) } }
        }

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
        val favoriteFlightRoutes = favorites.mapNotNull { fav ->
            val from = byCode[fav.departureCode]
            val to = byCode[fav.destinationCode]
            if (from != null && to != null) FlightRoute(fav.id, from, to) else null
        }
        FlightSearchState(
            searchText = search.query.text,
            selectedAirport = search.query.selected,
            airports = search.suggestions,
            destinations = search.destinations,
            favorites = favorites,
            favoriteFlightRoutes = favoriteFlightRoutes,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        FlightSearchState()
    )



    init {
        viewModelScope.launch {
            val saved = flightSearchPrefsRepo.searchText.first()
            if (query.value.text.isEmpty()) query.update {
                Query(text = saved)
            }
            query
                .map { it.text }
                .drop(1)
                .debounce(300.milliseconds)
                .distinctUntilChanged()
                .collect {
                    try {
                        flightSearchPrefsRepo.saveSearchPreferences(it)
                    } catch (e: IOException) {
                        Log.e(TAG, "Couldn't save search text", e)
                    }
                }
        }
    }

    fun onAirportSelected(airport: Airport) = query.update { Query(text = airport.iataCode, selected = airport) }

    fun onValueChanged(value: String) = query.update { Query(text = value) }

    fun onFavorite(flightRoute: FlightRoute) = viewModelScope.launch {
        if (flightRoute.isIn(uiState.value.favorites)) {
            favoriteRepository.removeFavorite(
                departureCode = flightRoute.from.iataCode,
                destinationCode = flightRoute.to.iataCode
            )
        } else {
            favoriteRepository.addFavorite(
                Favorite(
                    departureCode = flightRoute.from.iataCode,
                    destinationCode = flightRoute.to.iataCode
                )
            )
        }
    }

    companion object {
        private const val TAG = "FlightSearchViewModel"
        val Factory = viewModelFactory {
            initializer {
                val flightSearchApplication: FlightSearchApplication =
                    (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    flightSearchApplication.appContainer.airportRepository,
                    flightSearchApplication.appContainer.favoriteRepository,
                    flightSearchApplication.appContainer.flightSearchPrefsRepo,
                )
            }
        }
    }
}


