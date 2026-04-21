package edu.monash.fit5046.energysaver.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormScreen() {
    var userName by remember { mutableStateOf("") }
    var selectedApplianceType by remember { mutableStateOf("Lighting") }
    var dailyGoal by remember { mutableStateOf(50f) }
    var receiveAlerts by remember { mutableStateOf(false) }
    var userGoal by remember { mutableStateOf("") }

    var isDropdownExpanded by remember { mutableStateOf(false) }
    val applianceCategories = listOf("Lighting", "HVAC", "Kitchen", "Entertainment")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mobile Forms: Setup Profile", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Appliance Nickname") },
            modifier = Modifier.fillMaxWidth()
        )

        // DatePicker UI Mock (Basic Requirement)
        var isDateDialogVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = "01 Jan 2025",
            onValueChange = {},
            readOnly = true,
            label = { Text("Date of Birth / Registration") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { isDateDialogVisible = true }) {
                    Icon(Icons.Filled.DateRange, "Select Date")
                }
            }
        )

        if (isDateDialogVisible) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { isDateDialogVisible = false },
                confirmButton = {
                    TextButton(onClick = { isDateDialogVisible = false }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { isDateDialogVisible = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // Expanded Dropdown Requirement
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedApplianceType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Primary Category (SDG Goal 7)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                applianceCategories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedApplianceType = category
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Extra Components (Slider, Switch, Checkbox, Radio Button)
        Text("Daily Energy Goal: ${dailyGoal.toInt()} kWh")
        Slider(
            value = dailyGoal,
            onValueChange = { dailyGoal = it },
            valueRange = 1f..100f
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = receiveAlerts,
                onCheckedChange = { receiveAlerts = it }
            )
            Text("Subscribe to Smart Meter Analytics")
        }

        // Inline Error validation
        OutlinedTextField(
            value = userGoal,
            onValueChange = { userGoal = it },
            label = { Text("Monthly Budget ($)") },
            isError = userGoal.toFloatOrNull() == null && userGoal.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        if (userGoal.toFloatOrNull() == null && userGoal.isNotEmpty()) {
            Text("Please enter a valid number", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Save action */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Create Record")
        }
    }
}