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
    var reminderTime by remember { mutableStateOf("20:30") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Advanced Features Settings", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Control background energy insights and daily reminder preferences",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.Filled.Sync, contentDescription = "WorkManager Sync")
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Background Energy Insights", style = MaterialTheme.typography.titleMedium)
                        Text("Periodically imports simulated smart meter readings and weather context.", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = backgroundSync, onCheckedChange = { backgroundSync = it })
                }
                LinearProgressIndicator(
                    progress = { if (backgroundSync) 0.72f else 0.12f },
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                )
                Text(if (backgroundSync) "Next background sample: 15 minutes" else "Enable to schedule periodic context updates")
            }
        }
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.Filled.Alarm, contentDescription = "Alarm Manager")
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Daily Energy Reminders", style = MaterialTheme.typography.titleMedium)
                        Text("Schedules reminders when daily usage or peak-price risk is high.", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = dailyAlarm, onCheckedChange = { dailyAlarm = it })
                }
                OutlinedTextField(
                    value = reminderTime,
                    onValueChange = { reminderTime = it },
                    label = { Text("Reminder time") },
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* Trigger Manual Sync */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Refresh Energy Insights")
        }
    }
}
