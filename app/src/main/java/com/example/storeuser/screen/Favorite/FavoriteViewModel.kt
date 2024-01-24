package com.example.storeuser.screen.Favorite

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.common.Resource
import com.example.storeuser.common.UIEvent
import com.example.storeuser.data.StoreRepository
import com.example.storeuser.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {

    private val _listFavoriteProduct = MutableStateFlow<List<Product>>(emptyList())
    val listFavoriteProduct = _listFavoriteProduct.asStateFlow()

// sửa trong repo, viewModel, screen !!!
    private val _uiEvent = MutableStateFlow<UIEvent?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    var isLoading:Boolean by mutableStateOf(true)

    init {
        getListFavoriteProduct()
    }


    private fun getListFavoriteProduct(){
        viewModelScope.launch {
            repository.getListFavoriteProduct().collect { result->
                when(result){
                    is Resource.Error ->{
                        Log.e("Tpoo", "vm: error")
                        isLoading = false
                        _uiEvent.value = UIEvent.ErrorEvent(message = result.message!!)
                    }
                    is Resource.Loading -> {
                        Log.e("Tpoo", "vm: loading")
                        isLoading = true
                    }
                    is Resource.Success -> {
                        Log.e("Tpoo", "vm: success")
                        isLoading = false
                        _listFavoriteProduct.value = result.data!!
                    }
                }
            }
        }
    }

    fun addProductToFavorite(product: Product) {
        viewModelScope.launch {
            repository.addProductToFavorite(product)
        }
    }

    fun deleteProductFromFavorite(productId: String){
        viewModelScope.launch {
            repository.deleteProductFromFavorite(productId)
        }
    }





//    fun getListFavoriteProduct(){
//        viewModelScope.launch {
//            val uid = fireAuth.uid
//            firestore.collection("favorite").document(uid!!).collection("product")
//                .get().addOnSuccessListener { task ->
//                    val list = mutableListOf<Product>()
//                    for (item in task.documents) {
//                        list.add(item.toObject<Product>()!!)
//                    }
//                    _listFavoriteProduct.value = list
//                }
//        }
//    }





}