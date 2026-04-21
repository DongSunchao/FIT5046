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
import java.util.Locale

private const val CARBON_FACTOR_KG_PER_KWH = 0.65

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
                                IconButton(onClick = {
                                    if (selectedRecord == record) selectedRecord = null
                                    if (editingRecord == record) editingRecord = null
                                    allRecords.remove(record)
                                }) {
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
                val previousEditingRecord = editingRecord
                if (previousEditingRecord == null) {
                    allRecords.add(newRecord)
                } else {
                    val index = allRecords.indexOf(previousEditingRecord)
                    if (index >= 0) {
                        allRecords[index] = newRecord
                        if (selectedRecord == previousEditingRecord) {
                            selectedRecord = newRecord
                        }
                    }
                }
                editingRecord = null
                showRecordDialog = false
            }
        )
    }
}

@Composable
private fun RecordDialog(initialValue: String?, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    val parsedParts = initialValue?.split("|")?.map { it.trim() } ?: emptyList()
    val initialAppliance = parsedParts.getOrNull(0).orEmpty()
    val initialCategory = parsedParts.getOrNull(1)?.takeIf { it.isNotBlank() } ?: "Kitchen"
    val initialUsage = parsedParts.getOrNull(2)
        ?.removeSuffix("kWh")
        ?.trim()
        ?.takeIf { it.isNotBlank() }
        ?: "1.0"

    var appliance by remember(initialValue) { mutableStateOf(initialAppliance) }
    var usage by remember(initialValue) { mutableStateOf(initialUsage) }
    var category by remember(initialValue) { mutableStateOf(initialCategory) }

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
                val usageText = String.format(Locale.US, "%.1f", kwh)
                val carbonText = String.format(Locale.US, "%.2f", kwh * CARBON_FACTOR_KG_PER_KWH)
                onSave("${appliance.ifBlank { "New appliance" }} | $category | $usageText kWh | $carbonText kg CO2e")
            }) {
                Text("Save")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
