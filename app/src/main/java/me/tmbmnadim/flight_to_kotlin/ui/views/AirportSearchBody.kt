package me.tmbmnadim.flight_to_kotlin.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tmbmnadim.flight_to_kotlin.R
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.ui.view_model.FlightState

@Composable
fun AirportSearchBody(
    modifier: Modifier = Modifier,
    flightState: FlightState,
    onclick: (Airport) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(flightState.airports) { airport ->
            ResultTile(
                onclick = {
                    onclick(airport)
                },
                airport = airport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
        }
    }
}

@Composable
fun ResultTile(
    modifier: Modifier = Modifier,
    airport: Airport,
    onclick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier
            .testTag("result_tile"),
        onClick = onclick
    ) {
        Row {
            Text(
                text = airport.iataCode,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Box(Modifier.width(8.dp))
            Text(
                text = airport.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirportSearchBodyPreview() {
    AirportSearchBody(
        flightState = FlightState(
            listOf(
                Airport(id = 0, iataCode = "AAA", name = "Airport A", passengers = 100),
                Airport(id = 1, iataCode = "BBB", name = "Airport B", passengers = 100),
            )
        )
    )
}