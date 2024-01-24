package com.example.storeuser.screen.OnBoard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeuser.data.OnBoardingPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val ds: OnBoardingPreferences
): ViewModel() {
    fun saveOnBoardingState(isCompleted:Boolean){
        viewModelScope.launch {
            ds.saveOnBoardingState(isCompleted)
        }
    }
}