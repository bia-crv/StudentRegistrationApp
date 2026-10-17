package com.example.studentregistrationapp
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()



    NavHost(
        navController = navController, startDestination = "home") {

        composable("home") {
            HomePage(navController)
            }

        composable("registration") {
            RegistrationPage(navController)
        }
        composable("search"){
            StudentSearchPage(navController)
        }

    }
}
