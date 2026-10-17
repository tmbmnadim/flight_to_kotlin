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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tmbmnadim.flight_to_kotlin.ui.theme.FlightToKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightToKotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FlightCard(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FlightCard(name: String, modifier: Modifier = Modifier) {
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
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Text(text = stringResource(R.string.depart),style = MaterialTheme.typography.bodyMedium)
                Row {
                    Text(text = "FVO", style = MaterialTheme.typography.labelMedium)
                    Box(Modifier.width(8.dp))
                    Text(text = "Some airport", style = MaterialTheme.typography.bodySmall)
                }
                Text(text = stringResource(R.string.arrive), style = MaterialTheme.typography.bodyMedium)
                Row {
                    Text(text = "FVO", style = MaterialTheme.typography.labelMedium)
                    Box(Modifier.width(8.dp))
                    Text(text = "Some airport", style = MaterialTheme.typography.bodySmall)
                }
            }
            Box(Modifier.width(12.dp))
            Icon(Icons.Filled.Star, contentDescription = "Localized description")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightCardPreview() {
    FlightToKotlinTheme {
        FlightCard("Android")
    }
}