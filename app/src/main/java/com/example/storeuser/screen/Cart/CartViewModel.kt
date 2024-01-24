package com.example.storeuser.screen.Cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.data.StoreRepository
import com.example.storeuser.model.Order
import com.example.storeuser.model.Product
import com.example.storeuser.model.ProductOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {

    private val _listCartProduct = MutableStateFlow<List<ProductOrder>>(emptyList())
    val listCartProduct = _listCartProduct.asStateFlow()


    init {
        getListCartProduct()
    }

    private fun getListCartProduct(){
        viewModelScope.launch {
            repository.getListCartProduct().collect{
                _listCartProduct.value = it
            }
        }
    }

    fun addProductToCart(product: ProductOrder){
        viewModelScope.launch {
            repository.addProductToCart(product)
        }
    }

    fun deleteProductFromCart(productId: String){
        viewModelScope.launch {
            repository.deleteProductFromCart(productId)
        }
    }

    fun addOrder(order: Order){
        viewModelScope.launch {
            repository.addOrder(order)
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}