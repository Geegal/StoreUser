package com.example.storeuser.screen.Profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fireauth: FirebaseAuth,
    private val firestore : FirebaseFirestore
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun getProfileUser() {
        viewModelScope.launch {
            try {
                val uid = fireauth.currentUser!!.uid
                val userSnapshot = firestore.collection("user").document(uid).get().await()
                val item = userSnapshot.toObject<User>()
                _user.value = item
            } catch (e: FirebaseFirestoreException){
                Log.e("Error", "DetailScreen: $e")
            }
        }
    }
}