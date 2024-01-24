package com.example.storeuser.screen.OrderDetail

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
class OrderDetailViewModel @Inject constructor(
   private val  repository: StoreRepository
) : ViewModel() {

    private  val _orderDetail = MutableStateFlow<Order?>(null)
    val orderDetail = _orderDetail.asStateFlow()

    fun getOrderDetail(id: String){
        viewModelScope.launch {
            repository.getOrderDetail(id = id).collect{
                _orderDetail.value = it
            }
        }
    }
}