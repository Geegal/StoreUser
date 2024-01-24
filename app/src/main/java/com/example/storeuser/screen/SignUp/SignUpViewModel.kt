package com.example.storeuser.screen.SignUp

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.common.Resource
import com.example.storeuser.common.UIEvent
import com.example.storeuser.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: StoreRepository
): ViewModel() {
    private val _uiEvent = MutableStateFlow<UIEvent?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    var isLoading by mutableStateOf(false)

    fun createUserWithEmailAndPassword(email: String, password: String, name: String)
    = viewModelScope.launch {
        repository.createUserWithEmailAndPassword(email,password,name).collect{result->
            when(result){
                is Resource.Error ->{
                    isLoading = false
                    _uiEvent.value = UIEvent.ErrorEvent(result.message!!)
                }
                is Resource.Loading -> {
                    isLoading = true
                }
                is Resource.Success -> {
                    isLoading = false
                    _uiEvent.value = UIEvent.SuccessEvent(result.message)
                }


            }
        }
    }

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }

    fun validateConfirmPassword(password: String, confirmPass: String): Boolean{
        return password == confirmPass
    }

    fun validateName(name: String): Boolean{
        return name.isNotEmpty()
    }
}