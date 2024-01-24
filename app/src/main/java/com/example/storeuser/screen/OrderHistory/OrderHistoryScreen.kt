package com.example.storeuser.screen.OrderHistory

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.common.EmptyContent
import com.example.storeuser.common.TopBar
import com.example.storeuser.model.Order
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.ui.theme.GrayBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(modifier: Modifier = Modifier, navController: NavController,
                       orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel()){

    val listOrder by orderHistoryViewModel.listOrder.collectAsState()

    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Order History", showBack = true){
                navController.popBackStack()
            }
        }
    ) { padding ->
        
        Column(modifier = modifier.padding(padding)) {

            if (listOrder.isNotEmpty()) {

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {

                    items(listOrder) { item ->
                        OrderCard(modifier = modifier, item = item) {
                            navController.navigate(NavScreen.OrderDetailScreen.route+"/${item.id}")
                        }
                    }
                }
            } else{
                EmptyContent()
            }
        }
    }
}

@Composable
fun OrderCard(modifier: Modifier, item: Order,onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(5.dp),
    elevation = CardDefaults.elevatedCardElevation(5.dp),
    modifier = modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(150.dp).clickable {
                                  onClick()
        },
    colors = CardDefaults.cardColors(containerColor = GrayBackground)) {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Text(
                text = item.id,
                modifier = modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = "Name: " + item.name, textAlign = TextAlign.Left, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal,
                fontSize = 12.sp)
            Text(text = "Total: " + item.total, textAlign = TextAlign.Left, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal,
                fontSize = 12.sp)
            Text(text = "Date: " + item.date, textAlign = TextAlign.Left, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal,
                fontSize = 12.sp)
            Text(text = "Address: " + item.address, textAlign = TextAlign.Left, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal,
                fontSize = 12.sp)
        }
    }
}
