package com.example.codeblockapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.codeblockapp.screens.CreateAlgorithmScreen
import com.example.codeblockapp.screens.MainScreen
import com.example.codeblockapp.screens.ScreenMakeName

@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController,
        startDestination = ScreenNavigation.Home.route)
    {
        composable(
            route = ScreenNavigation.Home.route
        ) {
            MainScreen(navController = navController)
        }

        composable(
            route = ScreenNavigation.MakeNameForAlgorithm.route
        ) {
            ScreenMakeName(navController = navController)
        }

        composable(
            route = ScreenNavigation.CreateAlgorithm.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType})
        ) {
            val name = it.arguments?.getString("name") ?: "New algorithm"
            CreateAlgorithmScreen(name, navController)
        }
    }
}