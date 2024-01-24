package com.example.storeuser.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class OwnerScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object OwnerHomeScreen: MainScreen(
        route = "owner_home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object DashboardScreen: MainScreen(
        route = "dashboard",
        title = "Dashboard",
        icon = Icons.Default.Menu
    )
    object OwnerOrderScreen: MainScreen(
        route = "owner_order",
        title = "Order",
        icon = Icons.Default.ShoppingCart
    )

}