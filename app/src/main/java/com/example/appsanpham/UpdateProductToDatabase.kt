package com.example.appsanpham

import android.util.Log
import com.google.firebase.database.FirebaseDatabase


fun updateProductInDatabase(productId: String, productName: String, productType: String, productPrice: String, productImageUrl: String, onComplete: () -> Unit) {
    val productRef = FirebaseDatabase.getInstance().getReference("products").child(productId)

    val productUpdates = hashMapOf<String, Any>(
        "productname" to productName,
        "producttype" to productType,
        "productprice" to productPrice,
        "productimage" to productImageUrl
    )

    // Thực hiện cập nhật
    productRef.updateChildren(productUpdates).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onComplete()
        } else {
            Log.e("Firebase", "Error updating product", task.exception)
        }
    }
}