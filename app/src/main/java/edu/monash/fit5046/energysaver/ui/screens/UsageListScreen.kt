package edu.monash.fit5046.energysaver.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UsageListScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedRecord by remember { mutableStateOf<String?>(null) }
    var showRecordDialog by remember { mutableStateOf(false) }
    var editingRecord by remember { mutableStateOf<String?>(null) }
    
    val allRecords = remember {
        mutableStateListOf(
            "Fridge | Kitchen | 1.5 kWh | 0.98 kg CO2e",
            "Washing Machine | Laundry | 2.0 kWh | 1.30 kg CO2e",
            "Heater | HVAC | 5.0 kWh | 3.25 kg CO2e",
            "TV | Entertainment | 0.5 kWh | 0.33 kg CO2e",
            "Oven | Kitchen | 1.2 kWh | 0.78 kg CO2e"
        )
    }

    val displayedRecords = allRecords.filter { it.contains(searchQuery, ignoreCase = true) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingRecord = null
                showRecordDialog = true
            }) {
                Icon(Icons.Filled.Add, "Add Record")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Energy Records", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Review saved appliance usage, update records, and track carbon impact",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search appliance, category, or carbon value") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(displayedRecords) { record ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedRecord == record) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else {
                                MaterialTheme.colorScheme.surface
                            }
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            val parts = record.split("|").map { it.trim() }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(parts.getOrElse(0) { record }, fontWeight = FontWeight.Bold)
                                    Text(parts.drop(1).joinToString(" • "), style = MaterialTheme.typography.bodySmall)
                                }
                                RadioButton(
                                    selected = selectedRecord == record,
                                    onClick = { selectedRecord = record }
                                )
                            }
                            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                                TextButton(onClick = { selectedRecord = record }) {
                                    Text("Select")
                                }
                                IconButton(onClick = {
                                    editingRecord = record
                                    showRecordDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { allRecords.remove(record) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showRecordDialog) {
        RecordDialog(
            initialValue = editingRecord,
            onDismiss = { showRecordDialog = false },
            onSave = { newRecord ->
                if (editingRecord == null) {
                    allRecords.add(newRecord)
                } else {
                    val index = allRecords.indexOf(editingRecord)
                    if (index >= 0) allRecords[index] = newRecord
                }
                showRecordDialog = false
            }
        )
    }
}

@Composable
private fun RecordDialog(initialValue: String?, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var appliance by remember { mutableStateOf(initialValue?.substringBefore("|")?.trim() ?: "") }
    var usage by remember { mutableStateOf("1.0") }
    var category by remember { mutableStateOf("Kitchen") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialValue == null) "Add energy record" else "Edit energy record") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = appliance, onValueChange = { appliance = it }, label = { Text("Appliance") })
                OutlinedTextField(value = usage, onValueChange = { usage = it }, label = { Text("Usage kWh") })
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val kwh = usage.toFloatOrNull() ?: 0f
                onSave("${appliance.ifBlank { "New appliance" }} | $category | ${"%.1f".format(kwh)} kWh | ${"%.2f".format(kwh * 0.65f)} kg CO2e")
            }) {
                Text("Save")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
