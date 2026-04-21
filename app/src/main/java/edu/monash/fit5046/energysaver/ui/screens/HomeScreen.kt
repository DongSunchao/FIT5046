package edu.monash.fit5046.energysaver.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Energy Dashboard", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Household energy, carbon impact, and context-aware saving actions",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            SummaryTile(
                title = "Today",
                value = "9.8 kWh",
                subtitle = "12% below goal",
                modifier = Modifier.weight(1f)
            )
            SummaryTile(
                title = "CO2e",
                value = "6.4 kg",
                subtitle = "2.1 kg saved",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Icon(Icons.Filled.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Context-aware recommendation", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Smart meter load is high, outdoor temperature is 32°C, and grid price is at peak rate.")
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    AssistChip(
                        onClick = {},
                        label = { Text("Smart meter dataset") },
                        leadingIcon = { Icon(Icons.Filled.ElectricBolt, contentDescription = null) }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Weather API") },
                        leadingIcon = { Icon(Icons.Filled.Thermostat, contentDescription = null) }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text("Apply Eco Mode")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Carbon Footprint Tracking", style = MaterialTheme.typography.titleMedium)
        Text("Monthly usage history based on saved household records", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray.copy(alpha = 0.2f))
                .drawBehind {
                    drawLine(
                        color = Color(0xFF1B7F5A),
                        start = Offset(0f, size.height),
                        end = Offset(size.width * 0.3f, size.height * 0.5f),
                        strokeWidth = 6f
                    )
                    drawLine(
                        color = Color(0xFF1B7F5A),
                        start = Offset(size.width * 0.3f, size.height * 0.5f),
                        end = Offset(size.width * 0.6f, size.height * 0.8f),
                        strokeWidth = 6f
                    )
                    drawLine(
                        color = Color(0xFF1B7F5A),
                        start = Offset(size.width * 0.6f, size.height * 0.8f),
                        end = Offset(size.width, size.height * 0.2f),
                        strokeWidth = 6f
                    )
                }
        ) {
            Text(
                "Monthly footprint trend (kg CO2e)",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Icon(Icons.Filled.Forest, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SDG 7 impact", style = MaterialTheme.typography.titleMedium)
                }
                Text("The app encourages affordable and clean energy behaviour through usage awareness, peak-rate alerts, and personalised reduction goals.")
            }
        }
    }
}

@Composable
private fun SummaryTile(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text(value, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}
