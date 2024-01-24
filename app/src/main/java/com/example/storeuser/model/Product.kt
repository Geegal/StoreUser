package com.example.storeuser.model

data class Product (
    val id: String? = null,
    val name: String? = null,
    val category: String? = null,
    val stock: Int = 0,
    val price: Float = 0f,
    val des: String? = null,
    val image: String? = null
)