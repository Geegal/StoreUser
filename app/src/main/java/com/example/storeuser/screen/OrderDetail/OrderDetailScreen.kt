package com.example.storeuser.screen.OrderDetail


import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.model.ProductOrder
import com.example.storeuser.ui.theme.GrayBorderStroke


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(id: String,modifier : Modifier = Modifier, navController: NavController,
    orderDetailViewModel: OrderDetailViewModel = hiltViewModel()){

    val orderDetail by orderDetailViewModel.orderDetail.collectAsState()

    LaunchedEffect(key1 = true){
        orderDetailViewModel.getOrderDetail(id)
    }
    
    Scaffold(
        topBar = {
            Surface(shadowElevation = 5.dp) {

                TopAppBar(
                    modifier = modifier
                        .fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                                modifier = modifier.size(24.dp)
                            )
                        }
                    },
                    title = {
                        Row(horizontalArrangement = Arrangement.Center) {
                            orderDetail?.let {
                                Text(
                                    text = it.id, fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    ),
                )
            }
        }
    ) { padding ->

        Column(
            modifier =  modifier.padding(padding)
        ) {

            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){

                item{
                    Column {

                      //  Divider(modifier = modifier.height(1.dp), color = GrayBorderStroke)

                        Row(
                            modifier = modifier
                                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                                .fillMaxWidth()
                        ){
                            Text(text = "Name", fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                            modifier = modifier.weight(0.2f), textAlign = TextAlign.Left)
                            Spacer(modifier = modifier.weight(0.5f))
                            orderDetail?.let {
                                Text(
                                    text = it.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = modifier.weight(0.3f),
                                textAlign = TextAlign.Right)
                            }

                        }
                    }
                }

                item{
                    Column {

                        Divider(modifier = modifier
                            .padding(16.dp),
                            thickness = 1.dp,color = GrayBorderStroke)

                        Row(
                            modifier = modifier
                                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                                .fillMaxWidth()
                        ){
                            Text(text = "Address", fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                                modifier = modifier.weight(0.2f), textAlign = TextAlign.Left)
                            Spacer(modifier = modifier.weight(0.5f))
                            orderDetail?.let {
                                Text(
                                    text = it.address,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = modifier.weight(0.3f),
                                    textAlign = TextAlign.Right)
                            }

                        }
                    }
                }

                item{
                    Column {

                        Divider(modifier = modifier
                            .padding(16.dp),
                            thickness = 1.dp,color = GrayBorderStroke)

                        Row(
                            modifier = modifier
                                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                                .fillMaxWidth()
                        ){
                            Text(text = "Date", fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                                modifier = modifier.weight(0.2f), textAlign = TextAlign.Left)
                            Spacer(modifier = modifier.weight(0.5f))
                            orderDetail?.let {
                                Text(
                                    text = it.date,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = modifier.weight(0.3f),
                                    textAlign = TextAlign.Right)
                            }

                        }
                    }
                }

                item{
                    Column {

                        Divider(modifier = modifier
                            .padding(16.dp),
                            thickness = 1.dp,color = GrayBorderStroke)

                        Row(
                            modifier = modifier
                                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                                .fillMaxWidth()
                        ){
                            Text(text = "Total", fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                                modifier = modifier.weight(0.2f), textAlign = TextAlign.Left)
                            Spacer(modifier = modifier.weight(0.5f))
                            orderDetail?.let {
                                Text(
                                    text = "${it.total}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = modifier.weight(0.3f),
                                    textAlign = TextAlign.Right)
                            }

                        }
                    }
                }

                item{
                    Column {

                        Divider(modifier = modifier
                            .padding(16.dp),
                            thickness = 1.dp,color = GrayBorderStroke)

                        Row(
                            modifier = modifier
                                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                                .fillMaxWidth()
                        ){
                            Text(text = "Product", fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                                modifier = modifier.weight(0.2f), textAlign = TextAlign.Left)

//                            Spacer(modifier = modifier.weight(0.5f))
//                            orderDetail?.let {
//                                Text(
//                                    text = it.date,
//                                    fontSize = 15.sp,
//                                    fontWeight = FontWeight.Normal,
//                                    modifier = modifier.weight(0.3f),
//                                    textAlign = TextAlign.Right)
//                            }

                        }

                        Column(modifier = modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxHeight()) {

                            orderDetail?.list?.forEach{ product ->
                                ProductCardOrderDetail(product = product )
                            }

                        }
                    }
                }

            }

        }
        
    }

}

@Composable
fun ProductCardOrderDetail(modifier: Modifier = Modifier,product: ProductOrder){
    Column {
        Spacer(modifier = modifier.height(5.dp))

        Divider(
            modifier = modifier.height(1.dp),
            color = GrayBorderStroke.copy(0.3f)
        )

        Spacer(modifier = modifier.height(5.dp))

        Row(modifier = modifier.fillMaxWidth().padding(8.dp)) {
            Text(text = "${product.name}", fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(0.3f), textAlign = TextAlign.Left)
            Spacer(modifier = modifier.weight(0.1f))
            Text(text = "Price: ${product.price}", fontSize = 15.sp, fontWeight = FontWeight.Normal,
                modifier = modifier.weight(0.3f), textAlign = TextAlign.Center)
            Spacer(modifier = modifier.weight(0.1f))
                Text(
                    text = "Quantity: ${product.quantity}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = modifier.weight(0.3f),
                    textAlign = TextAlign.Right)

        }
    }
}