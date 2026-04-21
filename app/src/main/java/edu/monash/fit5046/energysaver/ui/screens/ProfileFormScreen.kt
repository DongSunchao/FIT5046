package edu.monash.fit5046.energysaver.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormScreen() {
    var householdName by remember { mutableStateOf("") }
    var selectedApplianceType by remember { mutableStateOf("Lighting") }
    var dailyGoal by remember { mutableStateOf(50f) }
    var receiveAlerts by remember { mutableStateOf(false) }
    var monthlyBudget by remember { mutableStateOf("") }
    var occupancy by remember { mutableStateOf("3") }
    var selectedDateMillis by remember { mutableStateOf(1776729600000L) }

    var isDropdownExpanded by remember { mutableStateOf(false) }
    val applianceCategories = listOf("Lighting", "HVAC", "Kitchen", "Entertainment")
    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.US) }
    val profileStartDate = remember(selectedDateMillis) { dateFormatter.format(Date(selectedDateMillis)) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Household Energy Profile", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Set household preferences for personalised energy-saving advice",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = householdName,
            onValueChange = { householdName = it },
            label = { Text("Household name") },
            leadingIcon = { Icon(Icons.Filled.Home, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Used to personalise dashboard and energy tips") }
        )

        var isDateDialogVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = profileStartDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Profile start date") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { isDateDialogVisible = true }) {
                    Icon(Icons.Filled.DateRange, "Select Date")
                }
            }
        )

        if (isDateDialogVisible) {
            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)
            DatePickerDialog(
                onDismissRequest = { isDateDialogVisible = false },
                confirmButton = {
                    TextButton(onClick = {
                        selectedDateMillis = datePickerState.selectedDateMillis ?: selectedDateMillis
                        isDateDialogVisible = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { isDateDialogVisible = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedApplianceType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Highest energy category") },
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

        Text("Daily Energy Goal: ${dailyGoal.toInt()} kWh")
        Slider(
            value = dailyGoal,
            onValueChange = { dailyGoal = it },
            valueRange = 1f..100f
        )

        OutlinedTextField(
            value = occupancy,
            onValueChange = { occupancy = it },
            label = { Text("Household occupants") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = occupancy.toIntOrNull() == null && occupancy.isNotEmpty(),
            supportingText = { Text("Non-sensory context used with smart meter readings") }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = receiveAlerts,
                onCheckedChange = { receiveAlerts = it }
            )
            Icon(Icons.Filled.Notifications, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Receive peak-rate and high-usage alerts")
        }

        OutlinedTextField(
            value = monthlyBudget,
            onValueChange = { monthlyBudget = it },
            label = { Text("Monthly Budget ($)") },
            isError = monthlyBudget.toFloatOrNull() == null && monthlyBudget.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Inline validation prevents invalid numeric budgets") }
        )
        if (monthlyBudget.toFloatOrNull() == null && monthlyBudget.isNotEmpty()) {
            Text("Please enter a valid number", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Save action */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Save Profile")
        }
    }
}
