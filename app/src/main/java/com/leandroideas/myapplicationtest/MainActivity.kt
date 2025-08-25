package com.leandroideas.myapplicationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leandroideas.myapplicationtest.ui.theme.MyApplicationTestTheme

enum class AppScreen {
    LOGIN,
    WELCOME,
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.LOGIN.name,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable(AppScreen.LOGIN.name) {
                            LoginScreen(
                                navigateToWelcomeScreen = {
                                    navController.navigate(AppScreen.WELCOME.name)
                                }
                            )
                        }
                        composable(AppScreen.WELCOME.name) {
                            WelcomeScreen()
                        }
                    }
                }
            }
        }
    }
}
