package com.example.codeblockapp.navigation

sealed class ScreenNavigation(val route: String) {
    object Home: ScreenNavigation(route = "home_screen")
    object MakeNameForAlgorithm: ScreenNavigation(route = "make_name_for_algorithm_screen")
    object CreateAlgorithm: ScreenNavigation(route = "create_algorithm_screen/{name}") {
        fun createRoute(name: String): String {
            return "create_algorithm_screen/$name"
        }
    }
}