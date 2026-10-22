package me.tmbmnadim.flight_to_kotlin

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.ui.view_model.FlightSearchState
import me.tmbmnadim.flight_to_kotlin.ui.views.AirportSearchBody
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AirportSearchBodyTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val airport1: Airport = Airport(id = 0, iataCode = "AAA", name = "Airport A")
    val airport2: Airport = Airport(id = 1, iataCode = "BBB", name = "Airport B")
    val airports = listOf(airport1, airport2)

    @Test
    fun resultTileList_listsAllAirports() {
        composeTestRule.setContent {
            AirportSearchBody(
                flightState = FlightSearchState(
                    airports = airports
                )
            )
        }

        composeTestRule
            .onAllNodesWithTag("result_tile")
            .assertCountEquals(2)
    }

    @Test
    fun resultTileList_listsNoAirports() {
        composeTestRule.setContent {
            AirportSearchBody(
                flightState = FlightSearchState()
            )
        }

        composeTestRule
            .onAllNodesWithTag("result_tile")
            .assertCountEquals(0)
    }

    @Test
    fun resultTileList_clickReturnsCorrectAirport() {
        var clickedAirport: Airport? = null

        composeTestRule.setContent {
            AirportSearchBody(
                flightState = FlightSearchState(
                    airports = airports
                ),
                onclick = {
                    clickedAirport = it
                }
            )
        }

        composeTestRule
            .onAllNodesWithTag("result_tile")[0].performClick()
            .performClick()
        assertEquals(airport1, clickedAirport)
        composeTestRule
            .onAllNodesWithTag("result_tile")[1].performClick()
            .performClick()
        assertEquals(airport2, clickedAirport)

    }
}