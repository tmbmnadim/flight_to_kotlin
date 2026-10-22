package me.tmbmnadim.flight_to_kotlin

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Flight
import me.tmbmnadim.flight_to_kotlin.ui.view_model.FlightSearchState
import me.tmbmnadim.flight_to_kotlin.ui.views.FlightsBody
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlightsBodyTest {
    private fun airport(iataCode: String) = Airport(
        0,
        "test",
        iataCode,
    )

    private fun flight(id: Int, from: String, to: String) = Flight(id, airport(from), airport(to))

    @get:Rule
    val rule = createComposeRule()

    private fun setBody(state: FlightSearchState, onFavorite: (Flight) -> Unit = {}) =
        rule.setContent {
            FlightsBody(
                uiState = state,
                onFavorite = onFavorite
            )
        }

    @Test
    fun header_showsFlightFromXText() {
        setBody(FlightSearchState(searchText = "test"))
        rule.onNodeWithTag("flights_from_x").assertExists()
        rule.onNodeWithTag("flights_from_x").assertTextContains("Flights from test")
    }

    @Test
    fun header_showsFavoriteFlightText() {
        setBody(FlightSearchState())
        rule.onNodeWithTag("favorite_flights").assertExists()
        rule.onNodeWithTag("favorite_flights").assertTextContains("Favorite Flights")
    }

    @Test
    fun flightList_showsCorrectFlights() {
        val flight1 = flight(1, "AAA", "BBB")
        val flight2 = flight(2, "BBB", "CCC")
        setBody(FlightSearchState(possibleFlights = listOf(flight1, flight2), searchText = "something"))
        rule.onAllNodesWithTag("flight_card").assertCountEquals(2)
        rule.onNodeWithTag("flight_card_1").assertExists()
        rule.onNodeWithTag("flight_card_1").assertTextContains("AAA")
        rule.onNodeWithTag("flight_card_2").assertExists()
        rule.onNodeWithTag("flight_card_2").assertTextContains("BBB")

        val flight3 = flight(3, "CCC", "CCC")
        val flight4 = flight(4, "DDD", "DDD")
        setBody(FlightSearchState(favorites = listOf(flight3, flight4)))
        rule.onAllNodesWithTag("flight_card").assertCountEquals(2)
        // SINCE THE NEWEST CARD IS SUPPOSED TO BE SHOWN FIRST, THE ORDER IS REVERSED
        rule.onNodeWithTag("flight_card_2").assertExists()
        rule.onNodeWithTag("flight_card_2").assertTextContains("CCC")
        rule.onNodeWithTag("flight_card_1").assertExists()
        rule.onNodeWithTag("flight_card_1").assertTextContains("DDD")
    }

    @Test
    fun flightList_showsCorrectAmount() {
        val flight1 = flight(1, "AAA", "BBB")
        val flight2 = flight(2, "BBB", "CCC")
        setBody(FlightSearchState(possibleFlights = listOf(flight1, flight2)))
        rule.onAllNodesWithTag("flight_card").assertCountEquals(2)
    }

    @Test
    fun flightList_returnsCorrectValue() {
        val flight1 = flight(1, "AAA", "BBB")
        val flight2 = flight(2, "BBB", "CCC")
        var clickedFlight: Flight? = null
        setBody(FlightSearchState(possibleFlights = listOf(flight1, flight2))) {
            clickedFlight = it
        }
        rule.onNodeWithTag("flight_card_1").performClick()
        assert(clickedFlight == flight1)
        rule.onNodeWithTag("flight_card_2").performClick()
        assert(clickedFlight == flight2)
    }

    @Test
    fun flightCard_showsCorrectText() {
        val flight1 = flight(1, "AAA", "BBB")
        setBody(FlightSearchState(possibleFlights = listOf(flight1)))
        rule.onNodeWithTag("flight_card_1").assertExists()
        rule.onNodeWithTag("flight_card_1").assertTextContains("AAA")
    }
}