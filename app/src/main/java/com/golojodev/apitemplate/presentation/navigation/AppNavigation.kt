package com.golojodev.apitemplate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.golojodev.apitemplate.presentation.screens.DetailsScreen
import com.golojodev.apitemplate.presentation.screens.FavoriteScreen
import com.golojodev.apitemplate.presentation.screens.HomeScreen
import com.golojodev.apitemplate.presentation.screens.SettingsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home
    ) {
        composable<Screens.Home> {
            HomeScreen(
                contentType = contentType,
                onModelClicked = {
                    navController.navigate(
                        Screens.Details(Json.encodeToString(it))
                    )
                }
            ) {
                navController.navigate(Screens.Settings)
            }
        }
        composable<Screens.Details> {
            DetailsScreen(
                model = Json.decodeFromString(it.toRoute<Screens.Details>().model)
            ) {
                navController.popBackStack()
            }
        }
        composable<Screens.Favorite> {
            FavoriteScreen(
                onClicked = {
                    navController.navigate(
                        Screens.Details(Json.encodeToString(it))
                    )
                }
            )
        }
        composable<Screens.Settings> {
            SettingsScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}