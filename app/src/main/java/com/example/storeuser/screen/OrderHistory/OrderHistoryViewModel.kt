package com.example.storeuser.screen.OrderHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.data.StoreRepository
import com.example.storeuser.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val repository: StoreRepository
): ViewModel() {

    private val _listOrder = MutableStateFlow<List<Order>>(emptyList())
    val listOrder = _listOrder.asStateFlow()

    init{
        getListOrderHistory()
    }

    private fun getListOrderHistory(){
        viewModelScope.launch {
            repository.getOrderHistory().collect{
                _listOrder.value = it
            }
        }
    }

//    fun clearOrder(){
//        viewModelScope.launch {
//            repository.clearOrder()
//        }
//    }
}