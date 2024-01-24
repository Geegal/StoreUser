package com.example.storeuser.screen.Owner.OwnerHome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.common.Resource
import com.example.storeuser.common.UIEvent
import com.example.storeuser.data.StoreRepository
import com.example.storeuser.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerHomeViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) :ViewModel() {
    private val _listProduct = MutableStateFlow<List<Product>>(emptyList())
    val listProduct = _listProduct.asStateFlow()

    private val _uiEvent = MutableStateFlow<UIEvent?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    var _isLoading by mutableStateOf(true)

    init {
        getListProduct()
    }

    private fun getListProduct() {
        viewModelScope.launch {
            storeRepository.getListProduct().collect{result->
                when(result){
                    is Resource.Error -> {
                        _isLoading = false
                        _uiEvent.value = UIEvent.ErrorEvent(message = result.message!!)
                    }
                    is Resource.Loading -> {
                        _isLoading = true
                    }
                    is Resource.Success -> {
                        _isLoading = false
                        _listProduct.value = result.data!!
                    }
                }
            }
        }
    }
}