package com.example.storeuser.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.storeuser.model.Order
import com.example.storeuser.screen.Cart.CartScreen

import com.example.storeuser.screen.Detail.DetailScreen
import com.example.storeuser.screen.Favorite.FavoriteScreen
import com.example.storeuser.screen.Home.HomeScreen
import com.example.storeuser.screen.OnBoard.OnBoardScreen
import com.example.storeuser.screen.OrderDetail.OrderDetailScreen
import com.example.storeuser.screen.OrderHistory.OrderHistoryScreen
import com.example.storeuser.screen.OrderSuccess.OrderSuccessScreen
import com.example.storeuser.screen.Owner.Dashboard.DashboardScreen
import com.example.storeuser.screen.Owner.OwnerAddProduct.OwnerAddProductScreen
import com.example.storeuser.screen.Owner.OwnerEditProduct.OwnerEditProductScreen
import com.example.storeuser.screen.Owner.OwnerHome.OwnerHomeScreen
import com.example.storeuser.screen.Owner.OwnerOrder.OwnerOrderScreen
import com.example.storeuser.screen.Profile.ProfileScreen
import com.example.storeuser.screen.Search.SearchScreen
import com.example.storeuser.screen.SignIn.SignInScreen
import com.example.storeuser.screen.SignUp.SignUpScreen
import com.example.storeuser.screen.Splash.Splash
import com.example.storeuser.screen.Splash.SplashScreen


@Composable
fun MainNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.SplashScreen.route){

        composable(MainScreen.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        composable(route = "${NavScreen.ProductDetailScreen.route}/{id}",
        arguments = listOf(
            navArgument("id"){
                type = NavType.StringType
            }
        )){ navBackStackEntry ->
            navBackStackEntry.arguments?.getString("id").let{
                DetailScreen(navController = navController, productId = it.toString())
            }

        }

        composable(route = "${NavScreen.OwnerEditProductScreen.route}/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )){ navBackStackEntry ->
            navBackStackEntry.arguments?.getString("id").let {
                OwnerEditProductScreen(navController = navController, productId = it.toString())
            }
        }

        composable(route = "${NavScreen.OrderDetailScreen.route}/{id}",
        arguments = listOf(
            navArgument("id"){
                type = NavType.StringType
            }
        )){navBackStackEntry ->
        navBackStackEntry.arguments?.getString("id").let {
            OrderDetailScreen(id = it.toString(), navController = navController)
            }
        }

        composable(route = NavScreen.OrderSuccessScreen.route){
            OrderSuccessScreen(navController = navController)
        }

        composable(route = NavScreen.SearchScreen.route){
            SearchScreen(navController = navController)
        }

        composable(route = MainScreen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }

        composable(route = MainScreen.FavoriteScreen.route){
            FavoriteScreen(navController = navController)
        }
        
        composable(route = NavScreen.SignInScreen.route){
            SignInScreen(navController = navController)
        }
        
        composable(route = MainScreen.CartScreen.route){
            CartScreen(navController = navController)
        }
        
        composable(route = NavScreen.OrderHistoryScreen.route){
            OrderHistoryScreen(navController = navController)
        }

        composable(route=NavScreen.OnBoardScreen.route){
            OnBoardScreen(navController = navController)
        }

        composable(route=NavScreen.SplashScreen.route){
            SplashScreen(navController = navController)
        }

        composable(route = OwnerScreen.OwnerOrderScreen.route){
            OwnerOrderScreen(navController = navController)
        }

        composable(route = OwnerScreen.OwnerHomeScreen.route){
            OwnerHomeScreen(navController = navController)
        }

        composable(route = OwnerScreen.DashboardScreen.route){
            DashboardScreen(navController = navController)
        }

        composable(route = NavScreen.AddProductScreen.route){
            OwnerAddProductScreen(navController = navController)
        }

        composable(route = NavScreen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
    }
}