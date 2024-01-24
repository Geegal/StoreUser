package com.example.storeuser.screen.OrderSuccess

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.storeuser.R
import com.example.storeuser.nav.MainScreen
import com.example.storeuser.nav.NavScreen
import kotlinx.coroutines.delay

@Composable
fun OrderSuccessScreen(modifier: Modifier = Modifier, navController: NavController){

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.orderconfirm))

    val progress by animateLottieCompositionAsState(composition = composition)
    
    LaunchedEffect(key1 = true){
        delay(2000)
        navController.navigate(MainScreen.HomeScreen.route){
            popUpTo(0)
        }


    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition, progress = { progress }, modifier = modifier.size(400.dp))
    }
}