package com.example.storeuser.screen.Owner.OwnerAddProduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.data.StoreRepository
import com.example.storeuser.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class OwnerAddProductViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {
    fun addProduct (product: Product){
        viewModelScope.launch {
            storeRepository.addProduct(product)
        }
    }

}