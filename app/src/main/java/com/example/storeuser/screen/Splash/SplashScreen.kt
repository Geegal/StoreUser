package com.example.storeuser.screen.Splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.R
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.ui.theme.Green
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = hiltViewModel()) {

    val onBoardCompleted by splashViewModel.onBoardingCompleted.collectAsState()
    val scale = remember{ Animatable(0f) }

    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue =1f,
            animationSpec = tween(
                durationMillis =1000,
                easing ={
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(1200L)
        navController.popBackStack()

        if (onBoardCompleted) navController.navigate(NavScreen.SignInScreen.route)
        else navController.navigate(NavScreen.OnBoardScreen.route)
    }
    Splash(scale = scale.value)
}

@Composable
fun Splash(
    modifier: Modifier = Modifier,
    scale: Float
) {
    Box(
        modifier = modifier
            .background(Green).
            fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .scale(scale)
                .padding(all = 64.dp),
            painter = painterResource(id = R.drawable.img_logo_app),
            contentDescription = "logo app"
        )
    }
}
