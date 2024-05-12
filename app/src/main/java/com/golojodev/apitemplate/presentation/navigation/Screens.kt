package com.golojodev.apitemplate.presentation.navigation

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Details : Screens("details")
    object Favorite : Screens("favorite")
}