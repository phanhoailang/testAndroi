package com.example.appsanpham

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


fun getProductFromDatabase(productId: String, callback: (DataProduct?) -> Unit) {
    val database = Firebase.database
    val myRef = database.getReference("products").child(productId)

    myRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val product = snapshot.getValue(DataProduct::class.java)
            callback(product)
        }

        override fun onCancelled(error: DatabaseError) {
            // Xử lý khi có lỗi xảy ra
            Log.e("Firebase", "Error fetching product", error.toException())
            callback(null)
        }
    })
}

