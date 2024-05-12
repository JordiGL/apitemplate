package com.golojodev.apitemplate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.golojodev.apitemplate.presentation.screens.DetailsScreen
import com.golojodev.apitemplate.presentation.screens.FavoriteScreen
import com.golojodev.apitemplate.presentation.screens.HomeScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ){
        composable(Screens.Home.route){
            HomeScreen(
                contentType = contentType
            ){
                navController.navigate(
                    "${Screens.Details.route}/${Json.encodeToString(it)}"
                )
            }
        }
        composable(
            "${Screens.Details.route}/{model}",
            arguments = listOf(
                navArgument("model"){
                    type = NavType.StringType
                }
            )
        ){
            DetailsScreen(
                model = Json.decodeFromString(it.arguments?.getString("model") ?: ""),
            ){
                navController.popBackStack()
            }
        }
        composable(Screens.Favorite.route) {
            FavoriteScreen()
        }
    }
}