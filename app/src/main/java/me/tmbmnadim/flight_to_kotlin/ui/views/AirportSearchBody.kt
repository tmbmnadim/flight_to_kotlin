package me.tmbmnadim.flight_to_kotlin.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tmbmnadim.flight_to_kotlin.R
import me.tmbmnadim.flight_to_kotlin.models.Airport

@Composable
fun AirportSearchBody(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(20) {
            ResultTile(
                airport = Airport(
                    id = 0,
                    iataCode = "AAA",
                    name = "From Airport",
                    passengers = 100
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ResultTile(
    modifier: Modifier = Modifier,
    airport: Airport,
) {
    Row(modifier) {
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

@Preview(showBackground = true)
@Composable
fun AirportSearchBodyPreview() {
    AirportSearchBody()
}