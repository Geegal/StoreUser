package com.example.storeuser.data

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.storeuser.common.Resource
import com.example.storeuser.model.Order
import com.example.storeuser.model.Product
import com.example.storeuser.model.ProductOrder
import com.example.storeuser.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class StoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val fireauth = FirebaseAuth.getInstance()


    suspend fun getListFavoriteProduct(): Flow<Resource<List<Product>>> {
        return callbackFlow {
            val uid = fireauth.currentUser!!.uid
            trySend(Resource.Loading())

            val snapshot = firestore.collection("favorite").document(uid!!).collection("product")
            snapshot.addSnapshotListener { value, error ->

                if (error != null) {

                    trySend(Resource.Error(message = error.toString()))
                    return@addSnapshotListener
                }

                if (value != null) {

                    val data = value.toObjects(Product::class.java)
                    this.trySend(Resource.Success(data = data))
                }

            }
            awaitClose { this.cancel() }
        }
    }

    suspend fun getListProduct(): Flow<Resource<List<Product>>> {
        return callbackFlow {
            trySend(Resource.Loading())

            val snapshot = firestore.collection("product")
            snapshot.addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Resource.Error(message = error.toString()))
                    return@addSnapshotListener
                }
                if (value != null) {
                    val data = value.toObjects(Product::class.java)
                    this.trySend(Resource.Success(data = data))
                }
            }
            awaitClose { this.cancel() }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun deleteProductFromFavorite(productId: String) {
        val uid = fireauth.currentUser!!.uid
        firestore.collection("favorite").document(uid!!).collection("product")
            .document(productId).delete()
    }

    fun deleteProductFromCart(productId: String) {
        val uid = fireauth.currentUser!!.uid
        firestore.collection("cart").document(uid!!).collection("product")
            .document(productId).delete()
    }


    fun getListCartProduct(): Flow<List<ProductOrder>> {
        return callbackFlow {
            val uid = fireauth.currentUser!!.uid

            val snapshot = firestore.collection("cart").document(uid!!).collection("product")
            snapshot.addSnapshotListener { value, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    val data = value.toObjects(ProductOrder::class.java)
                    this.trySend(data)
                }

            }
            awaitClose { this.cancel() }

        }
    }

    fun getOrderHistory(): Flow<List<Order>> {
        return callbackFlow {
            val uid = fireauth.currentUser!!.uid
            val orderColRef = firestore.collection("order")
            val query = orderColRef.whereEqualTo("uid", uid)
            query.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {

                    val dt = mutableListOf<Order>()
                    value.documents.forEach {
                        dt.add(
                            Order(
                                date = it["date"].toString(),
                                name = it["name"].toString(),
                                uid = it["uid"].toString(),
                                total = it["total"].toString().toFloat(),
                                id = it["id"].toString(),
                                list = it.get("list") as List<ProductOrder>,
                                address = it["address"].toString()
                            )
                        )
                    }
                    this.trySend(dt)
                }
            }
            awaitClose { this.cancel() }

        }
    }

    fun addProductToFavorite(product: Product) {
        val uid = fireauth.currentUser!!.uid
        firestore.collection("favorite").document(uid!!).collection("product")
            .document(product.id!!).set(product)
    }

    fun addProductToCart(product: ProductOrder) {
        val uid = fireauth.currentUser!!.uid
        val productRef = firestore.collection("cart").document(uid!!).collection("product")
        val query = productRef.whereEqualTo("id", product.id)
        query.get().addOnSuccessListener { querySnapShot ->
            if (querySnapShot.isEmpty) {
                productRef.document(product.id!!).set(product)
            } else {
                val existProduct = querySnapShot.documents[0].toObject(ProductOrder::class.java)
                existProduct?.let { exist ->
                    exist.quantity += product.quantity
                    productRef.document(exist.id!!).set(exist)
                }
            }
        }
    }

    fun addOrder(order: Order) {
        val orderDocRef = firestore.collection("order").document(order.id)

        orderDocRef.set(order)
    }

    fun clearCart() {
        val uid = fireauth.currentUser!!.uid
        val cartColRef = firestore.collection("cart").document(uid!!).collection("product")

        cartColRef.get().addOnSuccessListener {
            for (document in it.documents) {
                document.reference.delete()
            }
        }
    }

    fun clearOrder() {
        val orderColRef = firestore.collection("order")
        orderColRef.get().addOnSuccessListener {
            for (document in it.documents) {
                document.reference.delete()
            }
        }
    }

    fun getOrderDetail(id: String): Flow<Order> {
        return callbackFlow {
            val docRef = firestore.collection("order").document(id)

            docRef.addSnapshotListener { value, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    val data = value.toObject(Order::class.java)
                    this.trySend(data!!)
                }

            }
            awaitClose { this.cancel() }

        }

    }

    private fun createUser(name: String) {
        val newUid = fireauth.currentUser!!.uid
        val user = User(
            name = name,
            uid = newUid,
            email = fireauth.currentUser!!.email!!
        )
        firestore.collection("user").document(newUid).set(user)
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
//            fireauth.createUserWithEmailAndPassword(email, password).await().run {
//                createUser(name = name)
//            }
            fireauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    fireauth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                        createUser(name)
                    }
                }
            }
            emit(Resource.Success("Create account successfully! Please verify your email!"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }

    }

    suspend fun addProduct(product: Product) {
        try {
            val id = product.id
            firestore.collection("product").document(id!!).set(product).await()
        } catch (e: Exception) {
            Log.e("Tpoo", "Add Product: " + e)
        }
    }

    suspend fun getQuantityProductOrder(productId: String, dayBefore: String = "All"): Int {
        return suspendCancellableCoroutine { continuation ->
            val currentDateTime = LocalDateTime.now()
            // Định dạng ngày thành chuỗi
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formattedCurrentDate = currentDateTime.format(dateFormatter)

            var query = firestore.collection("order")
                .whereLessThanOrEqualTo("date",formattedCurrentDate)
            if (dayBefore != "All"){
                query = query.whereGreaterThanOrEqualTo("date", dayBefore)
            }

            var quantities = 0

            // Thực hiện truy vấn Firestore
            query
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents.documents) {
                        val order = document.toObject<Order>()
                        val filterProduct = order?.list?.filter { product -> productId == product.id }
                        filterProduct?.forEach { product ->
                            quantities += product.quantity
                        }
                    }
                    // Trả về kết quả thành công
                    continuation.resume(quantities)
                }
                .addOnFailureListener { e ->
                    // Trả về kết quả với lỗi
                    continuation.resumeWithException(e)
                }
        }
    }

}







