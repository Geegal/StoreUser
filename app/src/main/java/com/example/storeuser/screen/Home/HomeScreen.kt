package com.example.storeuser.screen.Home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items


import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.storeuser.R
import com.example.storeuser.common.BottomBar
import com.example.storeuser.common.ProductType
import com.example.storeuser.model.Product
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.ui.theme.Black
import com.example.storeuser.ui.theme.GrayBackground
import com.example.storeuser.ui.theme.GrayBorderStroke
import com.example.storeuser.ui.theme.GraySecondTextColor
import com.example.storeuser.ui.theme.Green
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import org.jetbrains.annotations.Async
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel(), modifier:Modifier = Modifier){
    val productType = listOf(
        ProductType.Vegetable.name,
        ProductType.Beverage.name,
        ProductType.Other.name,
    )

    val listProduct by homeViewModel.listProduct.collectAsState()



    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState()).padding(it)
        ) {

            HeaderLocationHome()

            VirtualSearchBar(){
                navController.navigate(NavScreen.SearchScreen.route)
            }

            SlideBanner()

            productType.forEach{ type->
                ListContentProduct(title= type,
                    products = listProduct.filter { product->
                        product.category!!.contains(type)
                    }, navController = navController)
            }

        }

        
    }
}

@Composable
fun ListContentProduct(title: String, products: List<Product>,navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement =Arrangement.Start) {
            androidx.compose.material.Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Green
            )

        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = PaddingValues(8.dp)){
            items(products){ product ->
                ProductCard(
                    productItem = product,
                    navController = navController,
//                    onClickToCart =onClickToCart
                )
            }
        }

    }
}

@Composable
fun ProductCard(productItem: Product, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = GrayBorderStroke),
        modifier = modifier
            .padding(12.dp)
            .width(174.dp)
            .clickable {
                navController.navigate(NavScreen.ProductDetailScreen.route + "/${productItem.id!!}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = productItem.image,
                contentDescription = "Image Product",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = productItem.name!!,

                fontWeight = FontWeight.Bold,
                color = Black,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))


            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$${productItem.price}",

                    fontWeight = FontWeight.Bold,
                    color = Black,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 18.sp
                )

//                Button(
//                    modifier = modifier.size(46.dp),
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Green),
//                    shape = RoundedCornerShape(14.dp),
//                    contentPadding = PaddingValues(10.dp),
//                    onClick = {
//                        onClickToCart.invoke(productItem)
//                    }
//                )
//                {
//                    Icon(
//                        modifier = Modifier.fillMaxSize(),
//                        imageVector = Icons.Default.Add,
//                        tint = Color.White,
//                        contentDescription ="Add"
//                    )
//                }
            }

        }
    }
}

@Composable
fun HeaderLocationHome(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier =  modifier.fillMaxWidth()) {

        Spacer(modifier = modifier.height(24.dp))

        Icon(painter = painterResource(id = R.drawable.ic_nectar), contentDescription = "Logo App",
            modifier = modifier
                .size(24.dp)
                .align(Alignment.CenterHorizontally), tint = Color.Unspecified)

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location",
                tint = Color.Red)

            Text(text = "Viet Nam", fontSize = 24.sp, color = Color.Black,
                fontWeight = FontWeight.SemiBold)

        }
    }
}


@Composable
fun VirtualSearchBar(onClick: () -> Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(12.dp),
        color = GrayBackground,
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .clickable { onClick.invoke() },
            horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Search", fontSize = 14.sp,color = GraySecondTextColor, fontWeight = FontWeight.Medium,
                modifier = Modifier

                    .padding(12.dp))

            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Black, modifier = Modifier.padding(12.dp) )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun SlideBanner(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = listOf(
        painterResource(id = R.drawable.img_banner1),
        painterResource(id = R.drawable.img_banner2),
        painterResource(id = R.drawable.img_banner3)
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column {
        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
                .height(114.dp)
                .fillMaxWidth()
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Image(
                    painter = imageSlider[page],
                    contentDescription = "Image Slider",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}