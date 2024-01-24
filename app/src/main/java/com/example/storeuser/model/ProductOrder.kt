package com.example.storeuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductOrder(
    val id: String? = null,
    val name: String? = null,
    val price: Float = 0f,
    var quantity: Int = 0,
    val image: String? = null
): Parcelable
