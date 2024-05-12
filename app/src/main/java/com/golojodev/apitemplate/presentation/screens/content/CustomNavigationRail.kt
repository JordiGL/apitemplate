package com.golojodev.apitemplate.presentation.screens.content

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.golojodev.apitemplate.presentation.navigation.Screens

@Composable
fun CustomNavigationRail(
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit,
    onDrawerClicked: () -> Unit
) {
    val items = listOf(Screens.Home, Screens.Favorite)
    val selectedItem = remember { mutableStateOf(items[0]) }
    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        NavigationRailItem(
            selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon"
                )
            }
        )
        NavigationRailItem(
            selected = selectedItem.value == Screens.Home,
            onClick = {
                onHomeClicked()
                selectedItem.value = Screens.Home
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            }
        )
        NavigationRailItem(
            selected = selectedItem.value == Screens.Favorite,
            onClick = {
                onFavoriteClicked()
                selectedItem.value = Screens.Favorite
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon"
                )
            }
        )
    }
}