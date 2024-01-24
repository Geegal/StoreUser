package com.example.storeuser.screen.Favorite

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.common.BottomBar
import com.example.storeuser.common.ContentCard
import com.example.storeuser.common.CustomDialog
import com.example.storeuser.common.EmptyContent
import com.example.storeuser.common.UIEvent
import com.example.storeuser.model.Product
import com.example.storeuser.ui.theme.Black
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(modifier: Modifier = Modifier,
                   navController: NavController, favoriteViewModel: FavoriteViewModel = hiltViewModel()){

    val listFavoriteProduct by favoriteViewModel.listFavoriteProduct.collectAsState()

    val snackbarHostState = remember{SnackbarHostState()}

    val isLoading = favoriteViewModel.isLoading

    LaunchedEffect(key1 = true){
        favoriteViewModel.uiEvent.collect { event->

            when(event){
                is UIEvent.ErrorEvent -> {

                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> {

                }
            }

        }
    }

    Scaffold(snackbarHost = { SnackbarHost (hostState =  snackbarHostState) },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        if (isLoading) {
            CustomDialog()
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                Text(
                    text = "My Favorite", fontWeight = FontWeight.Bold,
                    fontSize = 24.sp, color = Black,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )


                Spacer(modifier = modifier.height(16.dp))

                ListFavProduct(
                    navController = navController,
                    favProducts = listFavoriteProduct,
                    onClickDelete = { productItem ->
                        favoriteViewModel.deleteProductFromFavorite(productItem.id!!)
                    })

           }

        }
    }

}

@Composable
fun ListFavProduct(modifier: Modifier = Modifier, navController: NavController,
                   favProducts: List<Product>, onClickDelete: (Product) -> Unit) {

    if (favProducts.isNotEmpty()){
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(favProducts){item->
                ContentCard(
                    navController = navController,
                    favProduct = item,
                    onClickDelete = onClickDelete,

                )
            }
        }
    } else {
        EmptyContent()
    }
}