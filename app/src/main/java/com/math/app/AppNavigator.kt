package com.math.app

import com.math.app.ui.MainScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.math.app.ui.FaktlarScreen
import com.math.app.ui.MisollarScreen
import com.math.app.ui.OyinScreen
import com.math.app.ui.RanglarScreen
import com.math.app.ui.SanoqScreen
import com.math.app.ui.ShakllarScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(onNavigate = { route -> navController.navigate(route) })
        }
        composable("sanoq") { SanoqScreen() }
        composable("misollar") { MisollarScreen() }
        composable("shakllar") { ShakllarScreen() }
        composable("oyin") { OyinScreen() }
        composable("ranglar") { RanglarScreen() }
        composable("faktlar") { FaktlarScreen() }
    }
}
