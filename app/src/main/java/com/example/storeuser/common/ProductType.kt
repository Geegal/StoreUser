package com.example.storeuser.common

sealed class ProductType(
    val name:String
) {
    object Vegetable : ProductType("Vegetable")

    object Beverage: ProductType("Beverage")

    object Other: ProductType("Other")
}