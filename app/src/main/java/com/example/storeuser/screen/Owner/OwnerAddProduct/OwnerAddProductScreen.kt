package com.example.storeuser.screen.Owner.OwnerAddProduct

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.storeuser.R
import com.example.storeuser.common.CustomDialog
import com.example.storeuser.common.InputField
import com.example.storeuser.common.OwnerTopBar
import com.example.storeuser.model.Product
import com.example.storeuser.nav.OwnerScreen
import com.example.storeuser.ui.theme.Green
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


@SuppressLint("UnrememberedMutableState")
@Composable
fun OwnerAddProductScreen(modifier: Modifier = Modifier, navController: NavController,
                          ownerAddProductViewModel: OwnerAddProductViewModel = hiltViewModel()) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var isLoading by mutableStateOf(false)
    val context = LocalContext.current
    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
        }

    val storage =
        FirebaseStorage.getInstance().reference.child("images/${selectedImageUri?.lastPathSegment}")
    val name = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val stock = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val des = remember { mutableStateOf("") }


    Scaffold(topBar = {
        OwnerTopBar(navController = navController,
            title = "Add Product",
            showBack = true,
            addProduct = false,
            onBackClick = { navController.popBackStack() }
        )
    }) {

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .fillMaxSize()
        ) {
            // Spacer(modifier = modifier.height(80.dp))

            Card(
                modifier = modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(5.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                // colors = CardDefaults.cardColors(Color.Gray)
            ) {
                Box {
                    AsyncImage(
                        model = selectedImageUri, contentDescription = null,
                        modifier = modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )


                    IconButton(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = modifier
                            .size(30.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            painter = if (selectedImageUri != null) painterResource(id = R.drawable.edit_image)
                            else painterResource(id = R.drawable.add_image),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = modifier.size(30.dp)
                        )
                    }


                }

            }

            InputField(valueState = name, labelId = "Name")
            InputField(valueState = category, labelId = "Category")
            InputField(
                valueState = stock,
                labelId = "Stock",
                keyboardType = KeyboardType.Number
            )
            InputField(
                valueState = price,
                labelId = "Price",
                keyboardType = KeyboardType.Number
            )
            InputField(valueState = des, labelId = "Description", imeAction = ImeAction.Done)



            Column() {
                Button(
                    shape = RoundedCornerShape(24.dp),
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    onClick = {
                        if (selectedImageUri != null) {
                            isLoading = true
                            Log.e("Tpoo", "selectedImageUri: " + selectedImageUri)
                            storage.putFile(selectedImageUri!!)
                                .addOnSuccessListener {
                                    Log.e("Tpoo", "selectedImageUri: Success" )
                                    storage.downloadUrl.addOnSuccessListener { uri ->
                                        ownerAddProductViewModel.addProduct(
                                            Product(
                                                id = UUID.randomUUID().toString(),
                                                name = name.value,
                                                category = category.value,
                                                stock = stock.value.toInt(),
                                                price = price.value.toFloat(),
                                                des = des.value,
                                                image = uri.toString()
                                            )
                                        )
                                        Log.e("Tpoo", "Add: Success" )
                                        selectedImageUri = null
                                        name.value = ""
                                        category.value = ""
                                        stock.value = ""
                                        price.value = ""
                                        des.value = ""
                                        isLoading = false
                                        navController.navigate(OwnerScreen.OwnerHomeScreen.route)
                                    }
                                }
                        }
                    }
                ) {
                    Text(
                        text = "Add Product",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }

            }

        }
        if (isLoading){
            CustomDialog()
        }
    }
}
