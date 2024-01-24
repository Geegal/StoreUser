package com.example.storeuser.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object HomeScreen: MainScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object FavoriteScreen: MainScreen(
        route = "favorite",
        title = "Favorite",
        icon = Icons.Default.Favorite
    )

    object CartScreen: MainScreen(
        route = "cart",
        title = "Cart",
        icon = Icons.Default.ShoppingCart
    )

    object ProfileScreen: MainScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
}