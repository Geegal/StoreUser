package com.example.storeuser.screen.Owner.OwnerOrder

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.common.OwnerBottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OwnerOrderScreen(navController: NavController, modifier: Modifier = Modifier, ownerOrderViewModel: OwnerOrderViewModel = hiltViewModel()){
    
    Scaffold(
        bottomBar = { OwnerBottomBar(navController = navController)}
    ) {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(it)) {
            Text(text = "Order Screen")
        }
    }
   
}