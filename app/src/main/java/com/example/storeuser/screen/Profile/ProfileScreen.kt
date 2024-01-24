package com.example.storeuser.screen.Profile

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.R
import com.example.storeuser.common.BottomBar
import com.example.storeuser.common.TopBar
import com.example.storeuser.common.generateDummyAbout
import com.example.storeuser.model.AboutItem
import com.example.storeuser.model.User
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.ui.theme.Black
import com.example.storeuser.ui.theme.GrayBackground
import com.example.storeuser.ui.theme.GrayBorderStroke
import com.example.storeuser.ui.theme.GraySecondTextColor
import com.example.storeuser.ui.theme.Green

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel = hiltViewModel()){

    val user by profileViewModel.user.collectAsState()

    LaunchedEffect(key1 = true ){
        profileViewModel.getProfileUser()
    }
    Scaffold(
        bottomBar = { BottomBar(navController = navController)},

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Text(
                text = "Profile", fontWeight = FontWeight.Bold,
                fontSize = 24.sp, color = Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileCard(user = user)

            ListContentAbout(navController = navController)

            Spacer(modifier = Modifier.height(32.dp))

            ButtonLogout {
                navController.navigate(NavScreen.SignInScreen.route)
            }


        }
    }
}

@Composable
fun ListContentAbout(modifier: Modifier = Modifier,navController: NavController) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top =32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(generateDummyAbout()){ index, item ->
            ItemAbout(aboutItem = item) {
                when (index){
                    0 -> {
                        navController.navigate(NavScreen.OrderHistoryScreen.route)
                    }
                    else -> {
                        //
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Divider(modifier = Modifier.height(1.dp), color = GrayBorderStroke)
}

@Composable
fun ItemAbout(modifier: Modifier = Modifier,aboutItem: AboutItem, onClick:() -> Unit) {
    Column {
        Divider(modifier = Modifier.height(1.dp), color = GrayBorderStroke)

        Row(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .fillMaxWidth()
        ) {
            Image(
                modifier = modifier.size(20.dp),
                painter = painterResource(id = aboutItem.image),
                contentDescription = "image category"
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .weight(1f),
                text = aboutItem.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onClick()
                    },
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "next"
            )
        }
    }
}

@Composable
fun ProfileCard(modifier: Modifier = Modifier,user: User?) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Card(
            border = BorderStroke(width = 2.dp, color = Black),
            shape = RoundedCornerShape(40.dp),
        ) {
            Image(
                modifier = Modifier.height(80.dp),
                painter = painterResource(id = R.drawable.img_profile),
                contentDescription = "Image Profile"
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
        ) {

                Text(
                    text = user?.name ?: "Tpoo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Black
                )

            Text(
                text = user?.email ?: "Tpoo@gmail.com",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = GraySecondTextColor
            )
        }
    }
}

@Composable
fun ButtonLogout(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(16.dp)
            .height(48.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = GrayBackground),
        onClick = { onClick.invoke() }
    ) {
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription ="Logout",
            tint = Green
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "LOGOUT",
            color = Green,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
