package com.example.storeuser.nav

sealed class NavScreen(val route: String) {
    // general
    object SplashScreen: NavScreen("splash")
    object SignInScreen: NavScreen("signin")
    object SignUpScreen: NavScreen("signup")
    object OnBoardScreen: NavScreen("onboard")


    // customer
    object ProductDetailScreen: NavScreen("detail")
    object SearchScreen: NavScreen("search")
    object OrderHistoryScreen: NavScreen("orderhistory")
    object OrderDetailScreen: NavScreen("orderdetail")
    object OrderSuccessScreen: NavScreen("ordersuccess")



    //owner
    object AddProductScreen: NavScreen("add")
    object OwnerEditProductScreen: NavScreen("editproduct")
}