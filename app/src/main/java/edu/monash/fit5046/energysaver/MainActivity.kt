package edu.monash.fit5046.energysaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.monash.fit5046.energysaver.ui.navigation.MainScreen
import edu.monash.fit5046.energysaver.ui.screens.LoginScreen
import edu.monash.fit5046.energysaver.ui.screens.RegisterScreen
import edu.monash.fit5046.energysaver.ui.theme.EnergySaverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnergySaverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EnergySaverApp()
                }
            }
        }
    }
}

@Composable
fun EnergySaverApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("Main") {
                        popUpTo("Login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("Register") }
            )
        }
        composable("Register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("Main") {
                        popUpTo("Login") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable("Main") {
            MainScreen()
        }
    }
}