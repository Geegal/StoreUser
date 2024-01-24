package com.example.storeuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Order(
    val date: String = "",
    val name: String = "Manh Cuong",
    val uid: String = "",
    val list: List<ProductOrder> = emptyList(),
    val total: Float = 0f,
    val id: String = "",
    val address: String = ""
    ): Parcelable