package com.example.storeuser.screen.Owner.Dashboard


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.IconButton

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.R
import com.example.storeuser.common.BottomBar
import com.example.storeuser.common.CustomDialog
import com.example.storeuser.common.DaySection
import com.example.storeuser.common.OwnerBottomBar
import com.example.storeuser.common.generate1MonthBefore
import com.example.storeuser.common.generate3MonthsBefore
import com.example.storeuser.common.generate6MonthsBefore
import com.example.storeuser.common.generate7DayBefore
import com.example.storeuser.model.Product
import com.example.storeuser.ui.theme.BlueCard
import com.example.storeuser.ui.theme.ErrorColor
import com.example.storeuser.ui.theme.GrayBackground
import com.example.storeuser.ui.theme.Green

@SuppressLint("UnrememberedMutableState")
@Composable
fun DashboardScreen(modifier: Modifier = Modifier,navController: NavController,
                    dashboardViewModel: DashboardViewModel = hiltViewModel()){
    val listProduct by dashboardViewModel.listProduct.collectAsState()
    val listQuantity by dashboardViewModel.listQuantity.collectAsState()
    val isLoading = dashboardViewModel.isLoading


    var isDescending by remember { mutableStateOf(true) }
    val combineList = listProduct.zip(listQuantity)
    val listTotal = combineList.map { it.first.price* it.second }
    var sortedList = mutableStateOf(combineList.zip(listTotal).sortedByDescending { it.second })

    if (isDescending) {
        sortedList.value =  sortedList.value.sortedByDescending { it.second }
    } else {
        sortedList.value = sortedList.value.sortedBy { it.second }
    }


    val sortedProduct =   sortedList.value.map { it.first.first }
    val sortedQuantity = sortedList.value.map { it.first.second }
    val sortedTotal =  sortedList.value.map { it.second }

    val dayList = mutableListOf("All","7 Day", "1 Month", "3 Months", "6 Months")
    var isSelectedIndex by remember {
        mutableStateOf(0)
    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { OwnerBottomBar(navController = navController)}
    ) { paddingValue ->
        if (isLoading.value) {
            CustomDialog()
        } else {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                Column(modifier = modifier.padding(10.dp)) {
                    LazyRow(modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()){
                        items(count = dayList.size){ index ->
                            DaySection(day = dayList[index], selected = index == isSelectedIndex) {
                                isSelectedIndex = index
                                if (index == 1){
                                    dashboardViewModel.getQuantityProductOrder(dayBefore = generate7DayBefore())
                                } else if (index == 2){
                                    dashboardViewModel.getQuantityProductOrder(dayBefore = generate1MonthBefore())
                                } else if (index == 3){
                                    dashboardViewModel.getQuantityProductOrder(dayBefore = generate3MonthsBefore())
                                }else if (index == 4){
                                    dashboardViewModel.getQuantityProductOrder(dayBefore = generate6MonthsBefore())
                                } else{
                                    dashboardViewModel.getQuantityProductOrder()
                                }

                            }
                        }
                    }
                    
                    Spacer(modifier = modifier.height(10.dp))

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(BlueCard.copy(0.3f)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Rank",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.15f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Name Product",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.4f),
                            textAlign = TextAlign.Left

                        )
                        Text(
                            text = "Quantities",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            modifier = modifier.weight(0.25f),
                            textAlign = TextAlign.Center

                        )

                        Row(modifier = modifier.weight(0.25f), horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = "Total",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,

                                textAlign = TextAlign.Center
                            )
                            IconButton(onClick = {
                                isDescending = !isDescending

                            }, modifier = modifier.padding(0.dp)
                                ) {
                                Icon(imageVector =
                                    if (isDescending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                    modifier = modifier
                                        .size(30.dp)
                                        .fillMaxSize(),
                                    contentDescription = "orientation total",
                                    tint = if (isDescending) Green else ErrorColor)
                            }
                        }

                    }
                    Spacer(modifier = modifier.height(5.dp))
                    LazyColumn(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(sortedProduct.size) { index ->
                            DashboardProductCard(
                                index = index + 1,
                                product = sortedProduct[index],
                                quantities = sortedQuantity[index],
                                total = sortedTotal[index]
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardProductCard(modifier : Modifier = Modifier, index: Int, product: Product, quantities: Int, total: Float) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(GrayBackground),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "$index", fontSize = 17.sp, modifier = modifier.weight(0.15f),textAlign = TextAlign.Center)
        Text(text = "${product.name}",  fontSize = 17.sp,modifier = modifier.weight(0.4f),textAlign = TextAlign.Left)
        Text(text = "$quantities",  fontSize = 17.sp,modifier = modifier.weight(0.25f),textAlign = TextAlign.Center)
        Text(text = "${total}",  fontSize = 17.sp,modifier = modifier.weight(0.25f),textAlign = TextAlign.Center)
    }
}
