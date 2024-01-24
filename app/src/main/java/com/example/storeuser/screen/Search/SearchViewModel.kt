package com.example.storeuser.screen.Search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _listProduct = MutableStateFlow<List<Product>>(emptyList())
    val listProduct = _listProduct.asStateFlow()

    init {
        searchProduct("")
    }

    fun searchProduct(query: String){
        viewModelScope.launch {
            firestore.collection("product")
                .get()
                .addOnSuccessListener {  task ->
                    val list = mutableListOf<Product>()
                    for (item in task.documents){
                        val product = item.toObject<Product>()
                        list.add(product!!)
                    }

                    _listProduct.value = list.filter { product->
                        product.name!!.contains(query)
                    }
                }
        }
    }
}