package edu.monash.fit5046.energysaver.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import edu.monash.fit5046.energysaver.ui.screens.*

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    // Using BottomNavigationBar for navigation [HD requirement]
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                listOf(
                    Pair("Home", Icons.Filled.Home),
                    Pair("Usage", Icons.Filled.List),
                    Pair("Profile", Icons.Filled.Person),
                    Pair("Settings", Icons.Filled.Settings)
                ).forEach { (screen, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = screen) },
                        label = { Text(screen) },
                        selected = currentRoute == screen,
                        onClick = {
                            navController.navigate(screen) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "Home", Modifier.padding(innerPadding)) {
            composable("Home") { HomeScreen() }
            composable("Usage") { UsageListScreen() }
            composable("Profile") { ProfileFormScreen() }
            composable("Settings") { SettingsScreen() }
        }
    }
}