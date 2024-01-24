package com.example.storeuser.screen.Cart

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.storeuser.common.AddButton
import com.example.storeuser.common.BottomBar
import com.example.storeuser.common.EmptyContent
import com.example.storeuser.common.InputField
import com.example.storeuser.common.InputFieldInCart
import com.example.storeuser.common.QuantityDisplay
import com.example.storeuser.common.SpacerDividerContent
import com.example.storeuser.common.SubButton
import com.example.storeuser.model.Order
import com.example.storeuser.model.ProductOrder
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.ui.theme.Black
import com.example.storeuser.ui.theme.ErrorColor
import com.example.storeuser.ui.theme.GrayBorderStroke
import com.example.storeuser.ui.theme.Green
import com.example.storeuser.ui.theme.darkYellow
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID



@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CartScreen(modifier: Modifier = Modifier,navController: NavController, cartViewModel: CartViewModel = hiltViewModel()){

    val listCartProduct by cartViewModel.listCartProduct.collectAsState()

    var totalCost = 0f
    listCartProduct.forEach{
        totalCost += (it.price * it.quantity)
    }

    val uid = FirebaseAuth.getInstance().uid


    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val name = rememberSaveable() {
        mutableStateOf("")
    }
    val address = rememberSaveable() {
        mutableStateOf("")
    }

    val nameError: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val addressError: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current



        if (isSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {

                    Column(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Name",
                            fontSize = 13.sp,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Normal,
                            modifier = modifier.padding(start = 10.dp, bottom = 5.dp)

                        )

                        InputFieldInCart(valueState = name, labelId = "Name", isError = nameError)

                    }

                    Spacer(modifier = modifier.height(5.dp))

                    Column(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Address",
                            fontSize = 13.sp,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Normal,
                            modifier = modifier.padding(start = 10.dp, bottom = 5.dp)
                        )

                        InputFieldInCart(
                            valueState = address, labelId = "Address", imeAction = ImeAction.Done, isError = addressError,
                            onAction = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            )
                        )

                    }


                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(
                                text = "Price",
                                style = MaterialTheme.typography.body1,
                                color = darkYellow,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                            Spacer(modifier = modifier.height(5.dp))

                            Text(
                                text = "$${totalCost}",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold,
                                color = darkYellow,
                                fontSize = 22.sp
                            )
                        }

                        TextButton(
                            shape = RoundedCornerShape(10.dp),
                         //   modifier = modifier.height(25.dp).width(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Green
                            ),
                            onClick = {
                                nameError.value = name.value.isEmpty()
                                addressError.value = address.value.isEmpty()
                                if (!nameError.value && !addressError.value) {
                                    cartViewModel.addOrder(
                                        Order(
                                            date = LocalDateTime.now()
                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                            id = UUID.randomUUID().toString(),
                                            total = totalCost,
                                            list = listCartProduct,
                                            uid = uid!!,
                                            name = name.value,
                                            address = address.value
                                        )
                                    )

                                    cartViewModel.clearCart()
                                    isSheetOpen = false
                                    navController.navigate(NavScreen.OrderSuccessScreen.route)
                                }
                            }) {
                            Text(
                                text = "Buy now",
                                color = Color.White,
                                style = MaterialTheme.typography.button,
                                fontSize = 22.sp,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                            )
                        }

                    }
                    Spacer(modifier = modifier.height(10.dp))
                }
            }
        }


        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            }
        ) { padding ->
            Column {

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .weight(0.75f)
                        .padding(padding)
                ) {

                    Text(
                        text = "My Cart", fontWeight = FontWeight.Bold,
                        fontSize = 24.sp, color = Black,
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                    )


                    Spacer(modifier = modifier.height(16.dp))

                    ListCartProduct(
                        navController = navController,
                        cartProducts = listCartProduct,
                        onClickDelete = { productItem ->
                            cartViewModel.deleteProductFromCart(productItem.id!!)
                        },
                        onChangeQuantityClick = { product ->
                            cartViewModel.addProductToCart(product)
                        }
                    )

                }

                if (listCartProduct.isNotEmpty()) {

                    Column(modifier = modifier.weight(0.25f)) {
                        SpacerDividerContent()

                        Row(
                            modifier = modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Cost: ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = darkYellow
                            )
                            Text(
                                text = "$totalCost",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = darkYellow
                            )
                        }

                        Button(
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Green),
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = {
//                                cartViewModel.addOrder(
//                                    Order(
//                                        date = LocalDateTime.now()
//                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                                        id = UUID.randomUUID().toString(),
//                                        total = totalCost,
//                                        list = listCartProduct,
//                                        uid = uid!!
//                                    )
//                                )
//
//                                cartViewModel.clearCart()
                                isSheetOpen = true
                            }
                        ) {
                            Text(
                                text = "CHECKOUT",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = Color.White,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )
                        }
                    }
                }
            }

        }
    }



@Composable
fun ListCartProduct(modifier: Modifier = Modifier, navController: NavController,
                   cartProducts: List<ProductOrder>, onClickDelete: (ProductOrder) -> Unit,
                    onChangeQuantityClick:(ProductOrder) -> Unit) {


    if (cartProducts.isNotEmpty()){
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(cartProducts){item->
                ContentProductCart(
                    navController = navController,
                    cartProduct = item,
                    onClickDelete = onClickDelete,
                    onChangeQuantityClick = onChangeQuantityClick
                    )
            }

        }

    } else {
        EmptyContent()
    }
}

@Composable
fun ContentProductCart(modifier: Modifier = Modifier,navController: NavController,
                       cartProduct: ProductOrder, onClickDelete: (ProductOrder) -> Unit,
                        onChangeQuantityClick: (ProductOrder) -> Unit) {

    Column{

        Divider(modifier = modifier.height(1.dp), color = GrayBorderStroke)

        Row(modifier = modifier.fillMaxWidth()) {

            Row(modifier = modifier
                .weight(0.8f)
                .padding(top = 8.dp)
                .clickable {
                    navController.navigate(NavScreen.ProductDetailScreen.route + "/${cartProduct.id}")
                }
            ) {

                AsyncImage(
                    model = cartProduct.image,
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
                        text = cartProduct.name!!,
                        fontWeight = FontWeight.SemiBold,
                        color = Black,
                        fontSize = 16.sp
                    )

                    // Spacer(modifier = modifier.height(4.dp))


                }


            }

            Column(modifier = modifier
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text(
                    text = "$${cartProduct.price}",
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = 18.sp,
                )

                Spacer(modifier = modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    SubButton {
                        if (cartProduct.quantity!=1)
                            onChangeQuantityClick(cartProduct.copy(quantity = -1))
                    }
                    QuantityDisplay(number = cartProduct.quantity)
                    AddButton {
                        onChangeQuantityClick(cartProduct.copy(quantity = 1))
                    }
                }
            }

            Spacer(modifier = modifier.width(12.dp))

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable {
                        onClickDelete(cartProduct)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",

                colorFilter = ColorFilter.tint(color = ErrorColor)
            )

        }
    }

}
