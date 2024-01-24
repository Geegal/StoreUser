package com.example.storeuser.screen.Owner.Dashboard

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {
    private val _listProduct = MutableStateFlow<List<Product>>(emptyList())
    val listProduct = _listProduct.asStateFlow()
    private val _listQuantity = MutableStateFlow<List<Int>>(emptyList())
    val listQuantity = _listQuantity.asStateFlow()
    private val _uiEvent = MutableStateFlow<UIEvent?>(null)
    val uiEvent = _uiEvent.asStateFlow()
    var isLoading =  mutableStateOf(true)

    init {
        getListProduct()
    }

    private fun getListProduct() {
        viewModelScope.launch {
            storeRepository.getListProduct().collect{result->
                when(result){
                    is Resource.Error -> {
                        isLoading.value = false
                        _uiEvent.value = UIEvent.ErrorEvent(message = result.message!!)
                    }
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        _listProduct.value = result.data!!
                        getQuantityProductOrder()
                    }
                }
            }
        }
    }

    fun getQuantityProductOrder(dayBefore: String = "All"){
        viewModelScope.launch {
            isLoading.value = true
            _listQuantity.value = emptyList()
            _listProduct.value.forEach{ product ->
                val quantities = storeRepository.getQuantityProductOrder(product.id!!,dayBefore = dayBefore)
                Log.e("Tpoo: ","product: " + product.toString() + "-" + quantities)
                _listQuantity.value = _listQuantity.value.plus(quantities)
            }
            isLoading.value = false

        }
    }
}