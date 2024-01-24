package com.example.storeuser.screen.Owner.OwnerHome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.common.CustomDialog
import com.example.storeuser.common.EmptyContent
import com.example.storeuser.common.OwnerBottomBar
import com.example.storeuser.common.OwnerTopBar
import com.example.storeuser.common.ProductCardOwner
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.nav.OwnerScreen

@Composable
fun OwnerHomeScreen(navController: NavController,ownerHomeViewModel: OwnerHomeViewModel = hiltViewModel()){
    val listProduct by ownerHomeViewModel.listProduct.collectAsState()
    val isLoading = ownerHomeViewModel._isLoading

    Scaffold(topBar = {
        OwnerTopBar(
            navController = navController,
            title = "Home",
            showBack = false,
            addProduct = true,
            onAddClick = { navController.navigate(NavScreen.AddProductScreen.route) })
    }, bottomBar = {
        OwnerBottomBar(navController = navController)
    }) { padding->

        if (isLoading){
            CustomDialog()
        } else {
            Column(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {

                Spacer(modifier = Modifier.height(5.dp))

                if (listProduct.isNotEmpty()){
                    LazyColumn(modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)){
                        items(listProduct){ item ->
                            ProductCardOwner(product = item, onClick = { product ->
                                navController.navigate(NavScreen.OwnerEditProductScreen.route + "/${product.id}")
                            })
                        }
                    }
                } else {
                    EmptyContent()
                }
            }
        }


    }
}