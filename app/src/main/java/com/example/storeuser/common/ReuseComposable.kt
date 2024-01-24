package com.example.storeuser.common

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.storeuser.R
import com.example.storeuser.model.Product
import com.example.storeuser.nav.MainScreen
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.nav.OwnerScreen
import com.example.storeuser.ui.theme.Black
import com.example.storeuser.ui.theme.ErrorColor
import com.example.storeuser.ui.theme.GrayBackground
import com.example.storeuser.ui.theme.GrayBorderStroke
import com.example.storeuser.ui.theme.Green

@Composable
fun SpacerDividerContent() {
    Spacer(modifier = Modifier.height(8.dp))

    Divider(modifier = Modifier.height(1.dp))

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(248.dp),
            painter = painterResource(id = R.drawable.img_empty_content),
            contentDescription = "No data"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "Ooops! No Data",

            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Black,
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar( modifier: Modifier = Modifier,navController: NavController, title: String, showBack: Boolean, onBackClick: () -> Unit = {}){
    TopAppBar(
        title = {Text(text = title, fontSize = 26.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold,
            color = Black) },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = Green.copy(0.3f),
//        titleContentColor = White,
//        navigationIconContentColor = Black),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = GrayBackground.copy(0.7f)
        ),
        navigationIcon = {
            if (showBack){
                IconButton(onClick = {onBackClick()}) {

                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="Back",
                        modifier = modifier.size(30.dp))

                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerTopBar(modifier: Modifier = Modifier,navController: NavController, title: String,
                showBack: Boolean,addProduct: Boolean, onBackClick: () -> Unit = {},onAddClick:()->Unit={}){
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Green),
        modifier = modifier.background(Green),
        navigationIcon = {
            if (showBack){
                IconButton(onClick = {onBackClick()}) {

                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="Back",
                        modifier = modifier.size(30.dp))

                }
            }
        },
        actions ={
            if (addProduct) {
                IconButton(onClick = { onAddClick() }) {

                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add product",
                        modifier = modifier.size(30.dp))

                }

                IconButton(onClick = {navController.navigate(NavScreen.SignInScreen.route)}) {

                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout",
                        modifier = modifier.size(30.dp))

                }
            }
        }
    )
}

@Composable
fun OwnerBottomBar(modifier: Modifier = Modifier, navController: NavController){
    val navigationItems = listOf(
        OwnerScreen.OwnerHomeScreen,
        OwnerScreen.DashboardScreen,
        OwnerScreen.OwnerOrderScreen
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomBarDestination = navigationItems.any { it.route == currentRoute }

    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = White,
            contentColor = Color.Black,
            modifier = modifier
        ) {
            navigationItems.forEach { item ->
                val selectedNavItem = currentRoute?.contains(item.route) == true
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.SemiBold,
                            color = if (currentRoute == item.route) {
                                Green
                            } else Color.Black.copy(0.4f)
                        )
                    },
                    selectedContentColor = Green,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    selected = selectedNavItem,
                    onClick = {
                        if (!selectedNavItem) {

                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }

        }
    }
}


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val navigationItems = listOf(
        MainScreen.HomeScreen,
        MainScreen.FavoriteScreen,
        MainScreen.CartScreen,
        MainScreen.ProfileScreen
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomBarDestination = navigationItems.any { it.route == currentRoute }

    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = White,
            contentColor = Color.Black,
            modifier = modifier
        ) {
            navigationItems.forEach { item ->
                val selectedNavItem = currentRoute?.contains(item.route) == true
                BottomNavigationItem(
                    icon = {
                        androidx.compose.material.Icon(imageVector = item.icon, contentDescription = item.title)
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.SemiBold,
                            color = if (currentRoute == item.route) {
                                Green
                            } else Color.Black.copy(0.4f)
                        )
                    },
                    selectedContentColor = Green,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    selected = selectedNavItem,
                    onClick = {
                        if (!selectedNavItem) {

                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    inclusive = true}
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }

        }
    }
}


@Composable
fun ContentCard(modifier: Modifier = Modifier, navController: NavController,
                favProduct: Product, onClickDelete: (Product) -> Unit,
                )
{
    Column{

        Divider(modifier = modifier.height(1.dp), color = GrayBorderStroke)

        Row(modifier = modifier.fillMaxWidth()) {

            Row(modifier = modifier
                .weight(0.8f)
                .padding(top = 8.dp)
                .clickable {
                    navController.navigate(NavScreen.ProductDetailScreen.route + "/${favProduct.id}")
                }
            ) {

                AsyncImage(
                    model = favProduct.image,
                    contentDescription = "Image Product",
                    modifier = modifier
                        .size(width = 64.dp, height = 64.dp)
                        .padding(8.dp)
                )

                Column(
                    modifier = modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                ) {

                    Text(
                        text = favProduct.name!!,
                        fontWeight = FontWeight.SemiBold,
                        color = Black,
                        fontSize = 16.sp
                    )

                   // Spacer(modifier = modifier.height(4.dp))


                }

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "$${favProduct.price}",
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = 18.sp,
                )
            }
            
            Spacer(modifier = modifier.width(12.dp))

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable {
                        onClickDelete(favProduct)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                colorFilter = ColorFilter.tint(color = ErrorColor)
            )

        }
    }
}

@Composable
fun ProductCardOwner(modifier: Modifier = Modifier, product: Product, onClick:(Product) -> Unit){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(14.dp), shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = GrayBackground),
        elevation = CardDefaults.cardElevation(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {


            Row(
                modifier = modifier
                    .weight(0.8f)
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {

                AsyncImage(
                    model = product.image,
                    contentDescription = "Image Product",
                    modifier = modifier
                        .size(width = 80.dp, height = 80.dp)
                        .padding(8.dp)
                )

                Column(
                    modifier = modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                ) {

                    Text(
                        text = product.name!!,
                        fontWeight = FontWeight.SemiBold,
                        color = Black,
                        fontSize = 16.sp
                    )

                    // Spacer(modifier = modifier.height(4.dp))


                }

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "$${product.price}",
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = 18.sp,
                )
            }

            Spacer(modifier = modifier.width(12.dp))

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable {
                        onClick(product)
                    },
                imageVector = Icons.Default.Edit,
                contentDescription = "Delete",
                colorFilter = ColorFilter.tint(color = Color.DarkGray)
            )
        }

    }
}

@Composable
fun AddButton(modifier: Modifier = Modifier, onClick: () -> Unit){

        Image(imageVector = Icons.Default.Add, contentDescription = "add",
         modifier = modifier
             .size(25.dp)
             .clickable {
                 onClick()
             }
             .border(
                 BorderStroke(1.dp, GrayBorderStroke),
                 shape = RoundedCornerShape(topEnd = 3.dp, bottomEnd = 3.dp)
             ),
            colorFilter = ColorFilter.tint(color = Green)
        )


}

@Composable
fun SubButton(modifier: Modifier = Modifier, onClick: () -> Unit){
    Icon(painter = painterResource(id = R.drawable.remove_icon), contentDescription = "sub",
        modifier = modifier
            .size(25.dp)
            .clickable {
                onClick()
            }
            .border(
                BorderStroke(1.dp, GrayBorderStroke),
                shape = RoundedCornerShape(topStart = 3.dp, bottomStart = 3.dp)
            ), tint = ErrorColor
        )


}

@Composable
fun QuantityDisplay(modifier: Modifier = Modifier,number: Int){
    Surface(
        modifier = modifier.size(width = 40.dp, height = 25.dp),
        border = BorderStroke(1.dp, GrayBorderStroke),
        shape = RectangleShape,
    ) {
        Text(text = "$number", color = Black,modifier = modifier.padding(3.dp)
            , textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,

    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,

        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction)



}

@Composable
fun InputFieldInCart(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    isError: MutableState<Boolean>,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        trailingIcon = {
            if (isError.value)
                Icon(Icons.Filled.Warning, "error icon", tint = androidx.compose.material3.MaterialTheme.colorScheme.error)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction)

    if (isError.value) {
        Text(
            text = "Please fill in complete ${labelId} information",
            color = androidx.compose.material3.MaterialTheme.colorScheme.error,

            )
    }

}

@Composable
fun CustomDialog(){

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Dialog(onDismissRequest = { }) {

            val context = LocalContext.current
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                shape = RoundedCornerShape(16.dp)

            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        R.drawable.loader,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )
            }


        }
    }
}

@Composable
fun DaySection(
    day: String,
    selected: Boolean,
    onClick:() -> Unit
){
    val animateChipBackgroundColor by animateColorAsState(
        targetValue = if (selected) Green else GrayBackground,
        animationSpec = tween(
            durationMillis = if (selected) 100 else 50,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = animateChipBackgroundColor
            )
            .height(32.dp)
            .widthIn(min = 80.dp)
            .padding(horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick.invoke()
            }
    ) {
        Text(
            text = day,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = if (selected) White else Green
        )
    }
}