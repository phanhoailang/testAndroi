package com.example.appsanpham

import android.util.Log












import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun getAllProductsFromDatabase(callback: (List<DataProduct>) -> Unit) {
    val database = Firebase.database
    val myRef = database.getReference("products")

    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val productList = mutableListOf<DataProduct>()

            for (productSnapshot in snapshot.children) {
                val product = productSnapshot.getValue(DataProduct::class.java)
                product?.let {
                    productList.add(it)
                }
            }

            callback(productList)
        }

        override fun onCancelled(error: DatabaseError) {
            // Xử lý khi có lỗi xảy ra
            Log.e("Firebase", "Error fetching products", error.toException())
        }
    })
}