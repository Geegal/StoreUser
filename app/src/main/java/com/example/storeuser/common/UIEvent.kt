package com.example.storeuser.common

sealed class UIEvent {
    data class SuccessEvent(val message: String? = null): UIEvent()
    data class ErrorEvent(val message: String): UIEvent()
}