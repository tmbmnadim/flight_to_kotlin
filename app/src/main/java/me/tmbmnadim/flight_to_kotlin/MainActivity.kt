package me.tmbmnadim.flight_to_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.ui.theme.FlightToKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FlightToKotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FlightCard(
                        from = Airport(id = 0, iataCode = "AAA", name = "From Airport", passengers = 100),
                        to = Airport(id = 1, iataCode = "BBB", name = "To Airport", passengers = 100),
                        onFavorite = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
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
        val iconColor: Color = if (isFavorite) Color.Yellow else Color.Black

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
                Text(text = stringResource(R.string.depart),style = MaterialTheme.typography.bodyMedium)
                Row {
                    Text(text = from.iataCode, style = MaterialTheme.typography.labelMedium)
                    Box(Modifier.width(8.dp))
                    Text(text = from.name, style = MaterialTheme.typography.bodySmall)
                }

                // TO
                Text(text = stringResource(R.string.arrive), style = MaterialTheme.typography.bodyMedium)
                Row {
                    Text(text = to.iataCode, style = MaterialTheme.typography.labelMedium)
                    Box(Modifier.width(8.dp))
                    Text(text = to.name, style = MaterialTheme.typography.bodySmall)
                }
            }
            Box(Modifier.width(12.dp))
            IconButton(
                onClick = onFavorite,
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
fun FlightCardPreview() {
    FlightToKotlinTheme {
        FlightCard(
            from = Airport(id = 0, iataCode = "AAA", name = "From Airport", passengers = 100),
            to = Airport(id = 1, iataCode = "BBB", name = "To Airport", passengers = 100),
            onFavorite = {},
        )
    }
}