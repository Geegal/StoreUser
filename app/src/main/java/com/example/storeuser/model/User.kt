package com.example.storeuser.model

data class User(
    val name: String = "",
    val email: String = "",
    val uid: String = "",
    val avatar : String = "",
    val role : String = "customer"
)