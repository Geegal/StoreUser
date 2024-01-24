package com.example.storeuser.screen.OnBoard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button


import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.R
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.ui.theme.GraySecondTextColor
import com.example.storeuser.ui.theme.GrayTextColor
import com.example.storeuser.ui.theme.Green

@Composable
fun OnBoardScreen(navController: NavController, onBoardViewModel: OnBoardViewModel = hiltViewModel()){
    OnBoarding{
        navController.popBackStack()
        navController.navigate(NavScreen.SignInScreen.route)
        onBoardViewModel.saveOnBoardingState(true)
    }
}

@Composable
fun OnBoarding(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        Image(painter = painterResource(id = R.drawable.img_onboarding), contentDescription = "onboard image",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp),
            color = Color.Transparent
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome to our store",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 49.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center)

                Text(text = "Get your groceries in as fast as one hour",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = GrayTextColor,
                    textAlign = TextAlign.Center)
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .padding(start = 16.dp, end = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Get Started",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }



}
