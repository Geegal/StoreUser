package com.example.storeuser.screen.SignIn

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.storeuser.nav.MainScreen
import com.example.storeuser.nav.NavScreen
import com.example.storeuser.nav.OwnerScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val fireAuth: FirebaseAuth
): ViewModel() {
    var isLoading by mutableStateOf(false)

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }

    fun firebaseLogin(
        email: String,
        password: String,
        navController: NavController,
        context : Context
    ) {
        fireAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isLoading = true
                val isVerified = fireAuth.currentUser?.isEmailVerified
                if (isVerified == true) {
                    val db = Firebase.firestore
                    val doc = db.collection("user")
                        .whereEqualTo("email", fireAuth.currentUser!!.email)
                        .whereEqualTo("role", "customer")
                    doc.get().addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            isLoading = false
                            navController.navigate(MainScreen.HomeScreen.route){
                                popUpTo(NavScreen.SignInScreen.route){inclusive=true}
                            }

                        } else {
                            isLoading = false
                            navController.navigate(OwnerScreen.OwnerHomeScreen.route){
                                popUpTo(NavScreen.SignInScreen.route){inclusive=true}
                            }
                        }
                    }
                } else {
                    isLoading = false
                    Toast.makeText(context, "Please verify your email to sign in!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                isLoading = false
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}