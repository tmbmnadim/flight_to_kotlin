package me.tmbmnadim.flight_to_kotlin.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tmbmnadim.flight_to_kotlin.R

@Composable
fun FlightSearchScreen(
    modifier: Modifier = Modifier,
) {
    val isSearching: Boolean = true
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Box(Modifier.height(16.dp))
        OutlinedTextField(
            state = TextFieldState(),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(stringResource(R.string.search))
            },
            prefix = {
                Icon(Icons.Outlined.Search, contentDescription = "Search")
            },
            suffix = {
                Icon(Icons.Outlined.Mic, contentDescription = "Search")
            }
        )
        Box(Modifier.height(16.dp))
        if (isSearching)
            AirportSearchBody(Modifier.weight(1f))
        else
            FlightsBody(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun FlightSearchScreenPreview() {
    FlightSearchScreen()
}