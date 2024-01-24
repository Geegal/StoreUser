package com.example.storeuser.screen.Detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
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
class DetailViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val fireAuth: FirebaseAuth
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    private val _isFav = MutableStateFlow<Boolean>(false)
    val isFav = _isFav.asStateFlow()

    var isLoading: Boolean by mutableStateOf(true)

    // Resource<data,message>(Success,Loading,Error) vs UIEvent(Success and Error, "Loading hoặc gói trong DetailState(data, isLoading) )

    fun getProductDetail(id : String){
        viewModelScope.launch {

            try {
                isLoading = true
                val productSnapshot = firestore.collection("product").document(id).get().await()
                val item = productSnapshot.toObject<Product>()
                _product.value = item
                isLoading = false
            } catch (e: FirebaseFirestoreException){
                isLoading = false
                Log.e("Error", "DetailScreen: $e")
            }
        }
    }

    fun isFavorite(id: String){
        viewModelScope.launch {
            val uid = fireAuth.uid
            firestore.collection("favorite").document(uid!!).collection("product")
                .document(id).get().addOnCompleteListener{task->
                    if (task.isSuccessful){
                        if (task.result.exists()){
                            _isFav.value = true
                        }
                      else{
                            _isFav.value = false
                      }
                    }

                }
        }
    }
}