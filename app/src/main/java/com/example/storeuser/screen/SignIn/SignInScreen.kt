package com.example.storeuser.screen.SignIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.R
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.ui.theme.Black
import com.example.storeuser.ui.theme.ErrorColor
import com.example.storeuser.ui.theme.GrayBackground
import com.example.storeuser.ui.theme.GraySecondTextColor
import com.example.storeuser.ui.theme.Green
import com.example.storeuser.ui.theme.SignInColor
import kotlin.math.sin

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(navController: NavController, signInViewModel: SignInViewModel = hiltViewModel()){
    val context = LocalContext.current

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val passwordError: MutableState<Boolean> = remember { mutableStateOf(false) }

    val passwordFocusRequest =remember{FocusRequester()}

    val focus = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val isLoading = signInViewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f)
                    .background(White)
                    .padding(15.dp)
            ) {
                Image(
                    painter = key(R.drawable.img_logo_app) { painterResource(R.drawable.login_img) },
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(360.dp)
                )
            }



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .fillMaxSize()
                    .background(Green)
                    .verticalScroll(rememberScrollState())


            ) {

                Text(
                    text = "Sign In",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = White,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.padding(20.dp))
                EmailField(email = email, isError = emailError,
                    onAction = KeyboardActions {

                        passwordFocusRequest.requestFocus()
                    })


                Spacer(modifier = Modifier.padding(5.dp))
                PasswordField(password = password, isError = passwordError,
                    modifier = Modifier.focusRequester(passwordFocusRequest),
                    onAction = KeyboardActions(
                        onDone = {
                            focus.clearFocus()
                            keyboard?.hide()
                        }
                    )
                )
                Spacer(modifier = Modifier.padding(5.dp))

                Spacer(modifier = Modifier.padding(5.dp))
                Button(
                    onClick = {
                        emailError.value = !signInViewModel.validateEmail(email = email.value)
                        passwordError.value =
                            !signInViewModel.validatePassword(password = password.value)
                        if (!emailError.value && !passwordError.value) {
                            signInViewModel.firebaseLogin(
                                email = email.value,
                                password = password.value,
                                navController = navController,
                                context = context
                            )
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = SignInColor),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(60.dp), shape = RoundedCornerShape(14.dp)
                ) {
                    if (isLoading){
                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(color = White)
                        }
                    } else {
                        Text(
                            text = "Sign In",
                            fontSize = 20.sp,
                            color = Green
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))


                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "You don' have an account?",
                        fontSize = 17.sp,
                        color = White,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        "Sign up",
                        fontSize = 17.sp,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        color = Yellow,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { navController.navigate(NavScreen.SignUpScreen.route) })

                }
            }
        }


}


@Composable
fun EmailField(modifier: Modifier= Modifier,email: MutableState<String>, isError: MutableState<Boolean>,onAction: KeyboardActions = KeyboardActions.Default) {

    OutlinedTextField(
        value = email.value,
        onValueChange = { emailValue ->
            email.value = emailValue
            isError.value = false
        },
        placeholder = {
            Text(
                text = "Email",
                color = GraySecondTextColor
            )
        },
        keyboardOptions =
        KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        keyboardActions =onAction,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(0.8f),
        colors = TextFieldDefaults.colors(Black),
        trailingIcon = {
            if (isError.value)
                Icon(Icons.Filled.Warning, "error icon", tint = MaterialTheme.colorScheme.error)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                "email Icon", tint = Black
            )
        },
    )
    if (isError.value) {
        Text(
            text = "invalid email",
            color = MaterialTheme.colorScheme.error,

            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(modifier: Modifier= Modifier,password: MutableState<String>, title: String = "Password",
                  isError: MutableState<Boolean>,imeAction: ImeAction = ImeAction.Done, onAction: KeyboardActions = KeyboardActions.Default
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val focus = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.8f),
        value = password.value,
        onValueChange = { passwordValue ->
            password.value = passwordValue
            isError.value = false
        },
        trailingIcon = {

            if (isError.value)
                Icon(Icons.Filled.Warning, "error icon", tint = MaterialTheme.colorScheme.error)
            else {

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = painterResource(id =R.drawable.ic_password_eye),
                        "password visibility identifier",
                        tint = if (passwordVisibility) Black else GraySecondTextColor
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock_closed),
                "lock icon",
                tint = Black
            )
        },
        placeholder = {
            Text(
                text = title,
                color = GraySecondTextColor
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(Black),
        keyboardOptions =
        KeyboardOptions(
            imeAction = imeAction,
        ),
        keyboardActions = onAction,
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
    )

    if (isError.value) {
        Text(
            text =  "invalid pass",
            color = MaterialTheme.colorScheme.error,

            )
    }
}