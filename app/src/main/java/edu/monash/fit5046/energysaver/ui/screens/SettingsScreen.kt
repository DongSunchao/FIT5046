package edu.monash.fit5046.energysaver.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    var backgroundSync by remember { mutableStateOf(false) }
    var dailyAlarm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Advanced Features Settings", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(16.dp))

        // Advanced Feature 1: WorkManager
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Sync, contentDescription = "WorkManager Sync")
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Background Sync (WorkManager)", style = MaterialTheme.typography.titleMedium)
                        Text("Periodically fetches sensor data", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Switch(checked = backgroundSync, onCheckedChange = { backgroundSync = it })
            }
        }
        
        // Advanced Feature 2: Alarm Manager
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Alarm, contentDescription = "Alarm Manager")
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Daily Notifications (AlarmManager)", style = MaterialTheme.typography.titleMedium)
                        Text("Alerts when exceeding daily goals", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Switch(checked = dailyAlarm, onCheckedChange = { dailyAlarm = it })
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* Trigger Manual Sync */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Trigger Manual WorkManager Task")
        }
    }
}