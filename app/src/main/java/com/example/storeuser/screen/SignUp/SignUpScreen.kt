package com.example.storeuser.screen.SignUp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.storeuser.R
import com.example.storeuser.common.BottomBar
import com.example.storeuser.common.CustomDialog
import com.example.storeuser.common.UIEvent
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.screen.SignIn.EmailField
import com.example.storeuser.screen.SignIn.PasswordField
import com.example.storeuser.ui.theme.Black
import com.example.storeuser.ui.theme.ErrorColor
import com.example.storeuser.ui.theme.GraySecondTextColor
import com.example.storeuser.ui.theme.Green
import com.example.storeuser.ui.theme.SignInColor
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(navController: NavController, signUpViewModel: SignUpViewModel = hiltViewModel()){
    val context = LocalContext.current

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val rePassword = rememberSaveable { mutableStateOf("") }
    val name = rememberSaveable { mutableStateOf("") }


    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val passwordError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val rePasswordError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val nameError: MutableState<Boolean> = remember { mutableStateOf(false) }

    val focus = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    val isLoading = signUpViewModel.isLoading
    val snackbarHostState = remember{ SnackbarHostState() }

    LaunchedEffect(key1 = true){
        signUpViewModel.uiEvent.collect { event->

            when(event){
                is UIEvent.ErrorEvent -> {

                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is UIEvent.SuccessEvent ->{
                    snackbarHostState.showSnackbar(
                        message = event.message ?: "Create account successfully! Please verify your email to sign in!",
                        duration = SnackbarDuration.Short
                    )
                    delay(1000)
                    navController.navigate(NavScreen.SignInScreen.route){
                        popUpTo(NavScreen.SignInScreen.route){inclusive=true}
                    }
                }
                else -> {

                }
            }

        }
    }

    Scaffold(snackbarHost = { SnackbarHost (hostState =  snackbarHostState) },

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {

            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                    //   .fillMaxHeight(0.20f)
                    .background(Color.White)
                    .padding(15.dp)
            ) {
                Image(
                    painter = key(R.drawable.img_logo_app) { painterResource(R.drawable.login_img) },
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(200.dp)
                )
            }



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .fillMaxSize()
                    .background(Green)


            ) {

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .fillMaxSize()
                        .background(
                            Green
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {


                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        ),
                        color = Color.White,
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    EmailField(
                        email = email, isError = emailError,
                    )


                    Spacer(modifier = Modifier.padding(5.dp))
                    NameField(
                        name = name, isError = nameError
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    PasswordField(
                        password = password,
                        isError = passwordError,
                        imeAction = ImeAction.Next
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    PasswordField(
                        password = rePassword,
                        isError = rePasswordError,
                        onAction = KeyboardActions(
                            onDone = {
                                focus.clearFocus()
                                keyboard?.hide()
                            }
                        ),
                        title = "Confirm Password"
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        onClick = {
                            emailError.value = !signUpViewModel.validateEmail(email = email.value)
                            passwordError.value =
                                !signUpViewModel.validatePassword(password = password.value)
                            rePasswordError.value = !signUpViewModel.validateConfirmPassword(password = password.value,
                                confirmPass = rePassword.value)
                            nameError.value = !signUpViewModel.validateName(name = name.value)
                            if (!emailError.value && !passwordError.value && !rePasswordError.value && !nameError.value) {
                                signUpViewModel.createUserWithEmailAndPassword(
                                    email.value,
                                    password.value,
                                    name.value
                                )

                            }
                        },

                        colors = ButtonDefaults.buttonColors(containerColor = SignInColor),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(60.dp), shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 20.sp,
                            color = Green
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))


                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "You had an account?",
                            fontSize = 17.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            "Sign In",
                            fontSize = 17.sp,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Yellow,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { navController.navigate(NavScreen.SignInScreen.route){
                                popUpTo(NavScreen.SignInScreen.route){inclusive=true}
                            } })

                    }
                }
            }
        }
        if (isLoading) {
            CustomDialog()
        }
    }
}

@Composable
fun NameField(modifier: Modifier= Modifier,name: MutableState<String>, isError: MutableState<Boolean>,onAction: KeyboardActions = KeyboardActions.Default) {

    OutlinedTextField(
        value = name.value,
        onValueChange = { nameValue ->
            name.value = nameValue
            isError.value = false
        },
        placeholder = {
            Text(
                text = "Name",
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
                imageVector = Icons.Default.Person,
                "name Icon", tint = Black
            )
        },
    )
    if (isError.value) {
        Text(
            text = "invalid name",
            color = MaterialTheme.colorScheme.error,

            )
    }
}
