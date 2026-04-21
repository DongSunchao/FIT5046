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
    
    // Mock room data representing recorded energy usage entries
    val allRecords = remember {
        mutableStateListOf(
            "Fridge - 1.5 kWh",
            "Washing Machine - 2.0 kWh",
            "Heater - 5.0 kWh",
            "TV - 0.5 kWh",
            "Oven - 1.2 kWh"
        )
    }

    val displayedRecords = allRecords.filter { it.contains(searchQuery, ignoreCase = true) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Navigate to add record form */ }) {
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
            Text("Energy Records (Room DB)", style = MaterialTheme.typography.headlineMedium)
            
            Spacer(modifier = Modifier.height(16.dp))

            // Search Functionality
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search consumptions...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // Usage List with LazyColumn (CRUD operations mocked)
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(displayedRecords) { record ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(record, fontWeight = FontWeight.Bold)
                            Row {
                                IconButton(onClick = { /* Edit record */ }) {
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
}