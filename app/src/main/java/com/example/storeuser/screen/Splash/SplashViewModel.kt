package com.example.storeuser.screen.Splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.data.OnBoardingPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val ds: OnBoardingPreferences
): ViewModel() {
    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted = _onBoardingCompleted.asStateFlow()

    init {
        viewModelScope.launch {
            ds.getOnBoardingState.collect{
                _onBoardingCompleted.value = it
            }
        }
    }
}