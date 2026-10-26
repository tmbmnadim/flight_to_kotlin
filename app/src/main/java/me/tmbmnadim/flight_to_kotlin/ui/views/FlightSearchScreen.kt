package me.tmbmnadim.flight_to_kotlin.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.tmbmnadim.flight_to_kotlin.R
import me.tmbmnadim.flight_to_kotlin.ui.view_model.FlightSearchViewModel

@Composable
fun FlightSearchScreen(
    modifier: Modifier = Modifier,
    flightViewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.Factory)
) {
    val uiState = flightViewModel.uiState.collectAsState()

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Box(Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.value.searchText,
            onValueChange = flightViewModel::onValueChanged,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(stringResource(R.string.search))
            },
            prefix = {
                Icon(Icons.Outlined.Search, contentDescription = "Search")
            },
//            suffix = {
//                Icon(Icons.Outlined.Mic, contentDescription = "Search")
//            },
        )
        Box(Modifier.height(16.dp))
        if (uiState.value.searchText.isNotBlank() && uiState.value.selectedAirport == null)
            AirportSearchBody(
                Modifier.weight(1f),
                onclick = flightViewModel::onAirportSelected,
                flightState = uiState.value
            )
        else
            FlightsBody(
                Modifier.weight(1f),
                uiState = uiState.value,
                onFavorite = flightViewModel::onFavorite,
            )
    }
}

@Preview(showBackground = true)
@Composable
fun FlightSearchScreenPreview() {
    FlightSearchScreen()
}