package com.example.storeuser.screen.Detail

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card

import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.storeuser.R
import com.example.storeuser.common.AddButton
import com.example.storeuser.common.CustomDialog
import com.example.storeuser.common.QuantityDisplay
import com.example.storeuser.common.SpacerDividerContent
import com.example.storeuser.common.SubButton
import com.example.storeuser.model.Product
import com.example.storeuser.model.ProductOrder
import com.example.storeuser.screen.Cart.CartViewModel
import com.example.storeuser.screen.Favorite.FavoriteViewModel
import com.example.storeuser.ui.theme.Black
import com.example.storeuser.ui.theme.GrayBackground
import com.example.storeuser.ui.theme.GrayBackgroundSecond
import com.example.storeuser.ui.theme.GraySecondTextColor
import com.example.storeuser.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(modifier: Modifier = Modifier, navController: NavController,
                 detailViewModel: DetailViewModel = hiltViewModel(),
                 productId: String){

    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
    val cartViewModel = hiltViewModel<CartViewModel>()

    val product by detailViewModel.product.collectAsState()
    val isFav by detailViewModel.isFav.collectAsState()

    val isLoading = detailViewModel.isLoading
    val context = LocalContext.current
    var quantity by remember {
        mutableStateOf(1)
    }

    LaunchedEffect(true){
        detailViewModel.getProductDetail(productId)
        detailViewModel.isFavorite(productId)
    }

    Scaffold { padding ->

        if (isLoading) {
                    CustomDialog()
        } else {
            Column {
                Column(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .padding(padding)
                        .weight(1f)
                ) {
                    product?.let { productItem ->
                        DetailContentImageHeader(productItem = productItem)

                        Spacer(modifier = modifier.height(24.dp))

                        DetailContentDescription(productItem = productItem, isFav = isFav,
                            quantity = quantity, onAddClick = {
                                quantity += 1
                            },
                            onSubClick = {
                                if (quantity != 1) quantity -= 1
                            }) { product ->
                            if (isFav) {
                                favoriteViewModel.deleteProductFromFavorite(product.id!!)
                                detailViewModel.isFavorite(product.id)
                            } else {
                                favoriteViewModel.addProductToFavorite(product)
                                detailViewModel.isFavorite(product.id!!)
                            }
                        }
                    }

                }

                Column {
                    product?.let {
                        val productOrder = ProductOrder(
                            id = product!!.id,
                            name = product!!.name,
                            price = product!!.price,
                            image = product!!.image,
                            quantity = quantity
                        )
                        DetailButtonAddCart(
                            productItem = productOrder,
                            onClickToCart = { productItem ->
                                cartViewModel.addProductToCart(productItem)
                                Toast.makeText(context, "Add Product To Cart Success", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }

            }

        }
    }

}

@Composable
fun DetailButtonAddCart(modifier: Modifier=Modifier,productItem: ProductOrder, onClickToCart: (ProductOrder) -> Unit) {
    Button(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Green),
        onClick = {onClickToCart.invoke(productItem) }
    ) {
        Text(
            text = "Add To Cart",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun DetailContentDescription(productItem: Product, modifier: Modifier = Modifier, isFav: Boolean,
                             quantity: Int, onAddClick:()->Unit, onSubClick: ()->Unit,
     onFavClick: (Product) -> Unit) {
    Column(modifier = modifier.padding(16.dp)) {
        Row(modifier = modifier.fillMaxWidth()) {
            Column(modifier = modifier.weight(1f)) {
                Text(
                    text = productItem.name!!, fontSize = 24.sp, color = Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    SubButton() {
                        onSubClick()
                    }
                    QuantityDisplay(number = quantity)
                    AddButton() {
                        onAddClick()
                    }
                }

            }


            IconButton(onClick = { onFavClick(productItem) }) {
                Icon(
                    imageVector =
                    if (isFav) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = "add to favor",
                    modifier = modifier.align(Alignment.CenterVertically),
                    tint = Color.Red.copy(alpha = 0.6f)
                )


            }
        }

        Spacer(modifier = modifier.height(6.dp))

        Text(
            text = "$${productItem.price}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Black,
            modifier = modifier.align(
                Alignment.End
            )
        )

        SpacerDividerContent()

        Text(text = "Product Detail", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Black)

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = productItem.des!!,
            fontWeight = FontWeight.Medium,
            color = GraySecondTextColor,
            fontSize = 12.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))
        SpacerDividerContent()

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Nutritions",
                fontWeight = FontWeight.SemiBold,
                color = Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            Card(
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    "150gr",

                    fontWeight = FontWeight.SemiBold,
                    color = GraySecondTextColor,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .background(color = GrayBackgroundSecond)
                        .padding(4.dp)
                )
            }


        }

        SpacerDividerContent()


    }
}



@Composable
fun DetailContentImageHeader(productItem: Product, modifier: Modifier = Modifier) {
        Card(
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            backgroundColor = GrayBackground, modifier = modifier
                .blur(1.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = productItem.image, contentDescription = "Image product",
                modifier = modifier.height(353.dp),
                contentScale = ContentScale.Crop
            )
        }
}

