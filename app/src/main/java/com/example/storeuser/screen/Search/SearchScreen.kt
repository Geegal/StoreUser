package com.example.storeuser.screen.Search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.common.EmptyContent
import com.example.storeuser.common.TopBar
import com.example.storeuser.screen.Home.ProductCard
import com.example.storeuser.ui.theme.GrayBackground
import com.example.storeuser.ui.theme.GraySecondTextColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel(),
                 modifier: Modifier = Modifier){
    val listProduct by searchViewModel.listProduct.collectAsState()


    Scaffold(
    ) {
        Column(modifier = modifier.padding(it).fillMaxSize()) {
            SearchBar(navController = navController){ query->
                searchViewModel.searchProduct(query)
            }

            Spacer(modifier = modifier.height(16.dp))

            if (listProduct.isEmpty()){
                EmptyContent()
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = modifier.padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)){
                    items(listProduct){ product->
                        ProductCard(productItem = product, navController = navController)
                    }
                }
            }
            
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier,navController: NavController, onSearch: (String) -> Unit) {
    val query = rememberSaveable { mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = query.value, onValueChange = {
        query.value = it
        onSearch(query.value)
    },
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(50.dp)

            .clip(RoundedCornerShape(16.dp))
            ,
    placeholder = {
        Text(
            text = "Search",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = GraySecondTextColor
        )
    }, trailingIcon = {
        IconButton(onClick = { onSearch(query.value) }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search",
            tint = Color.Black)
        }
    },
        singleLine = true,
        colors =TextFieldDefaults.textFieldColors(
            containerColor = GrayBackground,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.Black,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            onSearch(query.value)
            keyboardController?.hide()
        }
    )

}
