package edu.monash.fit5046.energysaver.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Context-Awareness Mockup (Multiple Contexts)
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Icon(Icons.Filled.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Current Weather: Hot (32°C)", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("AC usage is high. Energy grid rate is at peak ($0.35/kWh).")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Simulated Context Adjust Action */ }) {
                    Text("Switch to Eco-Mode")
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Carbon Footprint Tracking", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Basic Visualisation (HD Core Function)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray.copy(alpha = 0.2f))
                .drawBehind {
                    // Line chart simulation
                    drawLine(
                        color = Color.Blue,
                        start = Offset(0f, size.height),
                        end = Offset(size.width * 0.3f, size.height * 0.5f),
                        strokeWidth = 4f
                    )
                    drawLine(
                        color = Color.Blue,
                        start = Offset(size.width * 0.3f, size.height * 0.5f),
                        end = Offset(size.width * 0.6f, size.height * 0.8f),
                        strokeWidth = 4f
                    )
                    drawLine(
                        color = Color.Blue,
                        start = Offset(size.width * 0.6f, size.height * 0.8f),
                        end = Offset(size.width, size.height * 0.2f),
                        strokeWidth = 4f
                    )
                }
        ) {
            Text(
                "Monthly Footprint (Kg CO2)",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}