package me.tmbmnadim.flight_to_kotlin.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tmbmnadim.flight_to_kotlin.R
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Flight
import me.tmbmnadim.flight_to_kotlin.ui.theme.FlightToKotlinTheme
import me.tmbmnadim.flight_to_kotlin.ui.view_model.FlightSearchState


@Composable
fun FlightsBody(
    modifier: Modifier = Modifier,
    uiState: FlightSearchState,
    onFavorite: (Flight) -> Unit = {}
) {
    Column(modifier) {
        if (uiState.searchText.isNotEmpty())
            Row(modifier = Modifier.testTag("flights_from_x")) {
                Text(
                    stringResource(R.string.flightsFrom),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    uiState.searchText,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        else
            Text(
                stringResource(R.string.favoriteFlights),
                modifier = Modifier.testTag("favorite_flights"),
                style = MaterialTheme.typography.titleMedium
            )
        Box(Modifier.height(16.dp))
        if(uiState.possibleFlights.isNotEmpty() || uiState.favoriteFlights.isNotEmpty())
        LazyColumn {
            items(
                if (uiState.searchText.isNotEmpty()) uiState.possibleFlights
                else uiState.favoriteFlights.reversed()
            ) { flight ->
                FlightCard(
                    from = flight.from,
                    to = flight.to,
                    onFavorite = { onFavorite(flight) },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .testTag("flight_card"),
                    isFavorite = flight.isIn(uiState.favorites),
                )
            }
        }
        else
            EmptyFlights()
    }
}


@Composable
fun EmptyFlights() {
    Text(stringResource(R.string.noFlights))
}

@Composable
fun FlightCard(
    from: Airport,
    to: Airport,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
) {
    Card(
        modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim
        ),
        shape = RoundedCornerShape(
            topEnd = 16.dp,
            topStart = 4.dp,
            bottomEnd = 4.dp,
            bottomStart = 4.dp
        ),
    ) {
        val iconColor: Color = if (isFavorite) Color(0xFFFFD700) else Color.Gray

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // FROM
                Text(
                    text = stringResource(R.string.depart),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    Text(text = from.iataCode, style = MaterialTheme.typography.labelMedium)
                    Box(Modifier.width(8.dp))
                    Text(text = from.name, style = MaterialTheme.typography.bodySmall)
                }

                // TO
                Text(
                    text = stringResource(R.string.arrive),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    Text(text = to.iataCode, style = MaterialTheme.typography.labelMedium)
                    Box(Modifier.width(8.dp))
                    Text(text = to.name, style = MaterialTheme.typography.bodySmall)
                }
            }
            Box(Modifier.width(12.dp))
            IconButton(
                onClick = onFavorite,
                modifier = Modifier
                    .testTag("favorite_button")
                    .semantics { selected = isFavorite },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = iconColor,
                )
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Favorite Button")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightsBodyPreview() {
    FlightToKotlinTheme {
        FlightsBody(uiState = FlightSearchState())
    }
}