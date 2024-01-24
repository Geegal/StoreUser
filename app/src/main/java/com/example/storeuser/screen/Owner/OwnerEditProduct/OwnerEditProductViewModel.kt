package com.example.storeuser.screen.Owner.OwnerEditProduct

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.common.Resource
import com.example.storeuser.data.StoreRepository
import com.example.storeuser.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class OwnerEditProductViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val firestore: FirebaseFirestore,
): ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    var isLoading by mutableStateOf(true)

     fun getProductWithId(id : String){
        viewModelScope.launch {
            isLoading = true
            try {
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

    fun updateProduct(product: Product){
        viewModelScope.launch {
            isLoading = true
            try {
                storeRepository.addProduct(product = product)
                isLoading = false
            } catch (e:Exception){
                isLoading = false
                Log.e("Tpoo", "Add Product: " + e)
            }
        }
    }

}